package com.SpringBootApp.UrlShortner.UnitTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.SpringBootApp.UrlShortner.dto.CreatedUrlDto;
import com.SpringBootApp.UrlShortner.dto.LongUrlDto;
import com.SpringBootApp.UrlShortner.dto.ShortUrlDto;
import com.SpringBootApp.UrlShortner.entity.Url;
import com.SpringBootApp.UrlShortner.exception.UrlNotFoundException;
import com.SpringBootApp.UrlShortner.mapper.UrlMapper;
import com.SpringBootApp.UrlShortner.repository.UrlRepository;
import com.SpringBootApp.UrlShortner.service.UrlAssembler;
import com.SpringBootApp.UrlShortner.service.UrlServiceImpl;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @Mock
    UrlRepository urlRepository;

    @Mock
    UrlMapper urlMapper;

    @Mock
    UrlAssembler urlAssembler;

    @InjectMocks
    UrlServiceImpl urlService;

    @Test
    public void shouldReturnLongUrlDtoWhenShortUrlExists() {
        String hash = "t1234";
        String shortUrl = "http://localhost:8080/t1234";
        String longUrl = "http://localhost:8080/abcd1234567";

        Url mockUrl = new Url(hash, shortUrl, longUrl);
        LongUrlDto expectedDto = new LongUrlDto(longUrl);
        ShortUrlDto shortUrlDto = new ShortUrlDto(shortUrl);

        Mockito.when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Mono.just(mockUrl));
        Mockito.when(urlMapper.toLongUrlDto(mockUrl)).thenReturn(expectedDto);

        Mono<LongUrlDto> result = urlService.getUrl(shortUrlDto);

        StepVerifier.create(result)
                .expectNextMatches(longUrlDto -> {
                    assertEquals(expectedDto.getLongUrl(), longUrlDto.getLongUrl());
                    return true;
                }).verifyComplete();
    }

    @Test
    public void shouldReturnUrlWhenLongUrlExists() {
        String hash = "t1234";
        String shortUrl = "http://localhost:8080/t1234";
        String longUrl = "http://localhost:8080/abcd1234567";
        Url mockUrl = new Url(hash, shortUrl, longUrl);
        LongUrlDto longUrlDto = new LongUrlDto(longUrl);
        CreatedUrlDto expectedDto = new CreatedUrlDto(1,shortUrl, longUrl);

        Mockito.when(urlRepository.findByLongUrl(longUrl)).thenReturn(Mono.just(mockUrl));
        Mockito.when(urlMapper.toCreatedUrlDto(mockUrl)).thenReturn(expectedDto);

        Mono<CreatedUrlDto> result = urlService.createUrl(longUrlDto);

        StepVerifier.create(result)
                .expectNextMatches(url -> {
                    assertEquals(expectedDto.getShortUrl(), url.getShortUrl());
                    assertEquals(expectedDto.getLongUrl(), url.getLongUrl());
                    return true;
                })
                .verifyComplete();

        verify(urlRepository).findByLongUrl(longUrl);
    }

    @Test
    public void shouldReturnNewlyCreatedUrlWhenLongUrlDoesNotExist() {
        String hash = "t1234";
        String shortUrl = "http://localhost:8080/t1234";
        String longUrl = "http://localhost:8080/abcd1234567";
        Url mockUrl = new Url(hash, shortUrl, longUrl);
        LongUrlDto longUrlDto = new LongUrlDto(longUrl);
        CreatedUrlDto expectedDto = new CreatedUrlDto(1,shortUrl, longUrl);

        Mockito.when(urlRepository.findByLongUrl(longUrl)).thenReturn(Mono.empty());
        Mockito.when(urlAssembler.assembleUrl(longUrl)).thenReturn(mockUrl);
        Mockito.when(urlRepository.save(mockUrl)).thenReturn(Mono.just(mockUrl));
        Mockito.when(urlMapper.toCreatedUrlDto(mockUrl)).thenReturn(expectedDto);

        Mono<CreatedUrlDto> result = urlService.createUrl(longUrlDto);
        StepVerifier.create(result)
                .expectNextMatches(url -> {
                    assertEquals(expectedDto.getShortUrl(), url.getShortUrl());
                    assertEquals(expectedDto.getLongUrl(), url.getLongUrl());
                    return true;
                })
                .verifyComplete();

        verify(urlRepository).findByLongUrl(longUrl);
        verify(urlAssembler).assembleUrl(longUrl);
        verify(urlRepository).save(mockUrl);
    }

    @Test
    public void shouldThrowUrlNotFoundExceptionWhenShortUrlDoesNotExist() {
        String shortUrl = "http://localhost:8080/t1234";
        ShortUrlDto shortUrlDto = new ShortUrlDto(shortUrl);
        String exceptionMessage = "URL not found for the given short URL: " + shortUrl;
        Mockito.when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Mono.empty());

        Mono<LongUrlDto> result = urlService.getUrl(shortUrlDto);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof UrlNotFoundException &&
                        throwable.getMessage().contains(exceptionMessage))
                .verify();
    }

    @Test
    public void shouldReturnNothingWhenShortUrlExists() {
        String shortUrl = "http://localhost:8080/t1234";
        ShortUrlDto shortUrlDto = new ShortUrlDto(shortUrl);
        Mockito.when(urlRepository.deleteByShortUrl(shortUrl)).thenReturn(Mono.just(true));
        StepVerifier.create(urlService.deleteUrl(shortUrlDto))
                .verifyComplete();
        verify(urlRepository).deleteByShortUrl(shortUrl);
    }

    @Test
    public void shouldThrowUrlNotFoundExceptionWhenShortUrlDoesNotExistForDeletion() {
        String shortUrl = "http://localhost:8080/t1234";
        ShortUrlDto shortUrlDto = new ShortUrlDto(shortUrl);
        String exceptionMessage = "URL not found for the given short URL: " + shortUrl;
        Mockito.when(urlRepository.deleteByShortUrl(shortUrl)).thenReturn(Mono.just(false));
        StepVerifier.create(urlService.deleteUrl(shortUrlDto))
                .expectErrorMatches(throwable -> throwable instanceof UrlNotFoundException &&
                        throwable.getMessage().contains(exceptionMessage))
                .verify();
        verify(urlRepository).deleteByShortUrl(shortUrl);
    }
}

package com.SpringBootApp.UrlShortner.UnitTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.SpringBootApp.UrlShortner.dto.UrlDto;
import com.SpringBootApp.UrlShortner.entity.Url;
import com.SpringBootApp.UrlShortner.exception.UrlNotFoundException;
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
    UrlAssembler urlAssembler;

    @InjectMocks
    UrlServiceImpl urlService;

    @Test
    public void shouldReturnUrlDtoWhenShortUrlExists() {
        String hash = "t1234";
        String shortUrl = "http://localhost:8080/t1234";
        String longUrl = "http://localhost:8080/abcd1234567";
        Url mockUrl = new Url(hash, shortUrl, longUrl);
        Mockito.when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Mono.just(mockUrl));

        Mono<UrlDto> result = urlService.getUrl(shortUrl);

        StepVerifier.create(result)
                .expectNextMatches(urlDto -> {
                    assertEquals(longUrl, urlDto.getUrl());
                    return true;
                }).verifyComplete();
    }

    @Test
    public void shouldReturnUrlWhenLongUrlExists() {
        String hash = "t1234";
        String shortUrl = "http://localhost:8080/t1234";
        String longUrl = "http://localhost:8080/abcd1234567";
        Url mockUrl = new Url(hash, shortUrl, longUrl);

        Mockito.when(urlRepository.findByLongUrl(longUrl)).thenReturn(Mono.just(mockUrl));
        Mono<Url> result = urlService.createUrl(longUrl);

        StepVerifier.create(result)
                .expectNextMatches(url -> {
                    assertEquals(mockUrl, url);
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

        Mockito.when(urlRepository.findByLongUrl(longUrl)).thenReturn(Mono.empty());
        Mockito.when(urlAssembler.assembleUrl(longUrl)).thenReturn(mockUrl);
        Mockito.when(urlRepository.save(mockUrl)).thenReturn(Mono.just(mockUrl));

        Mono<Url> result = urlService.createUrl(longUrl);
        StepVerifier.create(result)
                .expectNextMatches(url -> {
                    assertEquals(mockUrl, url);
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
        String exceptionMessage = "URL not found for the given short URL: " + shortUrl;
        Mockito.when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Mono.empty());

        Mono<UrlDto> result = urlService.getUrl(shortUrl);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof UrlNotFoundException &&
                        throwable.getMessage().contains(exceptionMessage))
                .verify();
    }

    @Test
    public void shouldReturnNothingWhenShortUrlExists() {
        String shortUrl = "http://localhost:8080/t1234";
        Mockito.when(urlRepository.deleteByShortUrl(shortUrl)).thenReturn(Mono.just(true));
        StepVerifier.create(urlService.deleteUrl(shortUrl))
                .verifyComplete();
        verify(urlRepository).deleteByShortUrl(shortUrl);
    }

    @Test
    public void shouldThrowUrlNotFoundExceptionWhenShortUrlDoesNotExistForDeletion() {
        String shortUrl = "http://localhost:8080/t1234";
        String exceptionMessage = "URL not found for the given short URL: " + shortUrl;
        Mockito.when(urlRepository.deleteByShortUrl(shortUrl)).thenReturn(Mono.just(false));
        StepVerifier.create(urlService.deleteUrl(shortUrl))
                .expectErrorMatches(throwable -> throwable instanceof UrlNotFoundException &&
                        throwable.getMessage().contains(exceptionMessage))
                .verify();
        verify(urlRepository).deleteByShortUrl(shortUrl);
    }
}

package com.SpringBootApp.UrlShortner.service;

import org.springframework.stereotype.Service;

import com.SpringBootApp.UrlShortner.dto.CreatedUrlDto;
import com.SpringBootApp.UrlShortner.dto.LongUrlDto;
import com.SpringBootApp.UrlShortner.exception.UrlNotFoundException;
import com.SpringBootApp.UrlShortner.mapper.UrlMapper;
import com.SpringBootApp.UrlShortner.repository.UrlRepository;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final UrlAssembler urlAssembler;
    private final UrlMapper urlMapper;

    public UrlServiceImpl(UrlRepository urlRepository, UrlAssembler urlAssembler, UrlMapper urlMapper) {
        this.urlRepository = urlRepository;
        this.urlAssembler = urlAssembler;
        this.urlMapper = urlMapper;
    }

    @Override
    public Mono<LongUrlDto> getUrl(String shortUrl) throws UrlNotFoundException {
        return urlRepository.findByShortUrl(shortUrl)
                .switchIfEmpty(Mono.error(new UrlNotFoundException("URL not found for the given short URL: " + shortUrl)))
                .map(urlMapper::toLongUrlDto);
    }

    @Override
    public Mono<CreatedUrlDto> createUrl(String longUrl) {
        return urlRepository.findByLongUrl(longUrl)
                .switchIfEmpty(
                    Mono.fromCallable(() -> urlAssembler.assembleUrl(longUrl))
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMap(urlRepository::save)
                )
                .map(urlMapper::toCreatedUrlDto);
    }

    @Override
    public Mono<Void> deleteUrl(String shortUrl) throws UrlNotFoundException {
        return urlRepository.deleteByShortUrl(shortUrl)
                .flatMap(urlToDeleteFound -> {
                    return (urlToDeleteFound) ?
                    Mono.empty() :
                    Mono.error(new UrlNotFoundException("URL not found for the given short URL: " + shortUrl));
                });
    }
}

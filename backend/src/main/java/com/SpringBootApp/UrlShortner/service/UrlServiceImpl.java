package com.SpringBootApp.UrlShortner.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.SpringBootApp.UrlShortner.dto.CreatedUrlDto;
import com.SpringBootApp.UrlShortner.dto.LongUrlDto;
import com.SpringBootApp.UrlShortner.dto.ShortUrlDto;
import com.SpringBootApp.UrlShortner.exception.UrlNotFoundException;
import com.SpringBootApp.UrlShortner.mapper.UrlMapper;
import com.SpringBootApp.UrlShortner.monitoring.MonitoringService;
import com.SpringBootApp.UrlShortner.repository.UrlRepository;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final UrlAssembler urlAssembler;
    private final UrlMapper urlMapper;
    private final MonitoringService monitoringService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlServiceImpl.class);

    public UrlServiceImpl(UrlRepository urlRepository, UrlAssembler urlAssembler, 
    UrlMapper urlMapper, MonitoringService monitoringService) {
        this.urlRepository = urlRepository;
        this.urlAssembler = urlAssembler;
        this.urlMapper = urlMapper;
        this.monitoringService = monitoringService;
    }

    @Override
    public Mono<LongUrlDto> getUrl(ShortUrlDto shortUrlDto) throws UrlNotFoundException {
        String shortUrl = shortUrlDto.getShortUrl();
        return urlRepository.findByShortUrl(shortUrl)
                .switchIfEmpty(Mono.error(new UrlNotFoundException("URL not found for the given short URL: " + shortUrl)))
                .map(urlMapper::toLongUrlDto);
    }

    @Override
    public Mono<CreatedUrlDto> createUrl(LongUrlDto longUrlDto) {
        String longUrl = longUrlDto.getLongUrl();
        return urlRepository.findByLongUrl(longUrl)
                .switchIfEmpty(
                    Mono.fromCallable(() -> urlAssembler.assembleUrl(longUrl))
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMap(urlRepository::save)
                    .doOnSuccess(value -> {
                        LOGGER.debug("URL successfully created");
                        monitoringService.incrementTotalUrlsShortened();
                    })
                )
                .map(urlMapper::toCreatedUrlDto);
    }

    @Override
    public Mono<Void> deleteUrl(ShortUrlDto shortUrlDto) throws UrlNotFoundException {
        String shortUrl = shortUrlDto.getShortUrl();
        return urlRepository.deleteByShortUrl(shortUrl)
                .flatMap(urlToDeleteFound -> {
                    return (urlToDeleteFound) ?
                    Mono.empty() :
                    Mono.error(new UrlNotFoundException("URL not found for the given short URL: " + shortUrl));
                });
    }
}

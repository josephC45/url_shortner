package com.SpringBootApp.UrlShortner.service;

import org.springframework.stereotype.Service;

import com.SpringBootApp.UrlShortner.dto.UrlDto;
import com.SpringBootApp.UrlShortner.entity.Url;
import com.SpringBootApp.UrlShortner.exception.UrlNotFoundException;
import com.SpringBootApp.UrlShortner.repository.UrlRepository;

import reactor.core.publisher.Mono;

@Service
public class UrlServiceImpl implements UrlService {

    private UrlRepository urlRepository;
    private UrlAssembler urlAssembler;

    public UrlServiceImpl(UrlRepository urlRepository, UrlAssembler urlAssembler) {
        this.urlRepository = urlRepository;
        this.urlAssembler = urlAssembler;
    }

    @Override
    public Mono<UrlDto> getUrl(String shortUrl) throws UrlNotFoundException {
        return urlRepository.findByShortUrl(shortUrl).map(url -> new UrlDto(url.getLongUrl()));
    }

    @Override
    public Mono<Url> createUrl(String longUrl) {
        return urlRepository.findByLongUrl(longUrl)
                .switchIfEmpty(
                        urlAssembler.assembleUrl(longUrl)
                                .flatMap(urlRepository::save));
    }

    @Override
    public Mono<Void> deleteUrl(String shortUrl) throws UrlNotFoundException {
        return urlRepository.findByShortUrl(shortUrl)
                .flatMap(url -> urlRepository.deleteById(url.getId()));
    }
}

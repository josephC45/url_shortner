package com.SpringBootApp.UrlShortner.service;

import com.SpringBootApp.UrlShortner.dto.UrlDto;
import com.SpringBootApp.UrlShortner.entity.Url;

import reactor.core.publisher.Mono;

public interface UrlService {

    Mono<Url> createUrl(String longUrl);

    Mono<UrlDto> getUrl(String shortUrl);

    Mono<Void> deleteUrl(String shortUrl);
}

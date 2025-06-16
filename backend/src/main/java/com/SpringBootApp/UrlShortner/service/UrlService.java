package com.SpringBootApp.UrlShortner.service;

import com.SpringBootApp.UrlShortner.dto.CreatedUrlDto;
import com.SpringBootApp.UrlShortner.dto.LongUrlDto;

import reactor.core.publisher.Mono;

public interface UrlService {

    Mono<CreatedUrlDto> createUrl(String longUrl);

    Mono<LongUrlDto> getUrl(String shortUrl);

    Mono<Void> deleteUrl(String shortUrl);
}

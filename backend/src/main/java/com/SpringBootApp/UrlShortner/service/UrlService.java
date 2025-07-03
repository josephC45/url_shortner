package com.SpringBootApp.UrlShortner.service;

import com.SpringBootApp.UrlShortner.dto.CreatedUrlDto;
import com.SpringBootApp.UrlShortner.dto.LongUrlDto;
import com.SpringBootApp.UrlShortner.dto.ShortUrlDto;

import reactor.core.publisher.Mono;

public interface UrlService {

    Mono<CreatedUrlDto> createUrl(LongUrlDto longUrl);

    Mono<LongUrlDto> getUrl(ShortUrlDto shortUrl);

    Mono<Void> deleteUrl(ShortUrlDto shortUrlDto);
}

package com.SpringBootApp.UrlShortner.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.SpringBootApp.UrlShortner.entity.Url;

import reactor.core.publisher.Mono;

@Repository
public interface UrlRepository extends R2dbcRepository<Url, Integer> {

    Mono<Url> findByLongUrl(String longUrl);

    Mono<Url> findByShortUrl(String shortUrl);

    Mono<Boolean> deleteByShortUrl(String shortUrl);
}

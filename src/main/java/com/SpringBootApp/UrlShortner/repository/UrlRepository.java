package com.SpringBootApp.UrlShortner.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.SpringBootApp.UrlShortner.entity.Url;

import reactor.core.publisher.Mono;

@Repository
public interface UrlRepository extends ReactiveCrudRepository<Url, Integer> {

    public Mono<Url> findByLongUrl(String longUrl);

    public Mono<Url> findByShortUrl(String shortUrl);

    public Mono<Void> deleteById(Integer id);
}

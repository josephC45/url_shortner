package com.SpringBootApp.UrlShortner.service;

import reactor.core.publisher.Mono;

public interface KeyCreator {

    public Mono<String> createKey();
    
}

package com.SpringBootApp.UrlShortner.service;

import com.SpringBootApp.UrlShortner.entity.Url;

import reactor.core.publisher.Mono;

public interface UrlAssembler {

    public Mono<Url> assembleUrl(String longUrl);
    
}

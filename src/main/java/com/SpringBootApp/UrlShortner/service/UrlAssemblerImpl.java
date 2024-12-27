package com.SpringBootApp.UrlShortner.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.SpringBootApp.UrlShortner.config.ConfigProp;
import com.SpringBootApp.UrlShortner.entity.Url;

import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(ConfigProp.class)
public class UrlAssemblerImpl implements UrlAssembler {

    private KeyCreator keyCreator;
    private ConfigProp configProp;

    public UrlAssemblerImpl(KeyCreator keyCreator, ConfigProp configProp) {
        this.keyCreator = keyCreator;
        this.configProp = configProp;
    }

    public Mono<Url> assembleUrl(String longUrl) {
        return keyCreator.createKey()
                .map(key -> {
                    String shortUrl = configProp.getShortUrlBase().concat(key);
                    return new Url(key, shortUrl, longUrl);
                });
    }
}

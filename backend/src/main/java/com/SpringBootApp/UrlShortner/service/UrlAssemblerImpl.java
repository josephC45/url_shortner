package com.SpringBootApp.UrlShortner.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.SpringBootApp.UrlShortner.config.ConfigProp;
import com.SpringBootApp.UrlShortner.entity.Url;

@Service
@EnableConfigurationProperties(ConfigProp.class)
public class UrlAssemblerImpl implements UrlAssembler {

    private final KeyCreator keyCreator;
    private final ConfigProp configProp;

    public UrlAssemblerImpl(KeyCreator keyCreator, ConfigProp configProp) {
        this.keyCreator = keyCreator;
        this.configProp = configProp;
    }

    @Override
    public Url assembleUrl(String longUrl) {
        String key = keyCreator.createKey();
        String shortUrl = configProp.getShortUrlBase().concat(key);
        return new Url(key, shortUrl, longUrl);
    }
}

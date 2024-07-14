package com.SpringBootApp.UrlShortner.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="app")
public class ConfigProp {

    private int baseConversion;
    private String shortUrlBase;

    public int getBaseConversion() {
        return baseConversion;
    }
    public void setBaseConversion(int baseConversion) {
        this.baseConversion = baseConversion;
    }
    public String getShortUrlBase() {
        return shortUrlBase;
    }
    public void setShortUrlBase(String shortUrlBase) {
        this.shortUrlBase = shortUrlBase;
    }
}

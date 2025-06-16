package com.SpringBootApp.UrlShortner.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class ConfigProp {

    private int baseConversion;
    private String shortUrlBase;
}

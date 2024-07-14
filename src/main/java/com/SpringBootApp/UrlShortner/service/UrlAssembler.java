package com.SpringBootApp.UrlShortner.service;

import com.SpringBootApp.UrlShortner.entity.Url;

public interface UrlAssembler {
    public Url assembleUrl(String longUrl);
}

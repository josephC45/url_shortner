package com.SpringBootApp.UrlShortner.service;

public interface KeyCreator {
    public long getCurrentDateTimeOffset();
    public String createKey();
}

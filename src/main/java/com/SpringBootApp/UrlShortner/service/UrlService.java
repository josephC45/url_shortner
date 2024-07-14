package com.SpringBootApp.UrlShortner.service;

import com.SpringBootApp.UrlShortner.entity.Url;

public interface UrlService {
    String handleDecoding(String url);
    Url createUrl(String longUrl);
    String getUrl(String shortUrl);
    void deleteUrl(String shortenedUrl);
}

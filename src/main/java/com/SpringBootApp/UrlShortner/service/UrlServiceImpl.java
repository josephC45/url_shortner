package com.SpringBootApp.UrlShortner.service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import com.SpringBootApp.UrlShortner.entity.Url;
import com.SpringBootApp.UrlShortner.repository.UrlRepository;

@Service
public class UrlServiceImpl implements UrlService {

    private UrlRepository urlRepository;
    private UrlAssembler urlAssembler;

    public UrlServiceImpl(UrlRepository urlRepository, UrlAssembler urlAssembler){
        this.urlRepository = urlRepository;
        this.urlAssembler = urlAssembler;
    }

    @Override
    public String handleDecoding(String url){
        String decodedString = URLDecoder.decode(url, StandardCharsets.UTF_8);
        return decodedString;
    }

    @Override
    public String getUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl);
        return (url != null) ? url.getLongUrl() : null;
    }

    @Override
    public Url createUrl(String longUrl) {
        if(urlRepository.findByLongUrl(longUrl) == null) {
            Url newlyCreatedUrl = urlAssembler.assembleUrl(longUrl);
            return urlRepository.save(newlyCreatedUrl);
        }
        return urlRepository.findByLongUrl(longUrl);
    }

    @Override
    public void deleteUrl(String shortenedUrl) {
        Url url = urlRepository.findByShortUrl(shortenedUrl);
        urlRepository.deleteById(url.getId());
    }

}

package com.SpringBootApp.UrlShortner.service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBootApp.UrlShortner.dto.UrlDto;
import com.SpringBootApp.UrlShortner.entity.Url;
import com.SpringBootApp.UrlShortner.exception.UrlNotFoundException;
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
    public UrlDto getUrl(String shortUrl) throws UrlNotFoundException {
        Url url = urlRepository.findByShortUrl(shortUrl).orElseThrow(() -> new UrlNotFoundException("URL was not found associated with provided short url"));
        return new UrlDto(url.getLongUrl());
    }

    @Override
    @Transactional
    public Url createUrl(String longUrl) {
        Url url = urlRepository.findByLongUrl(longUrl).orElseGet(() -> {
            Url newlyCreatedUrl = urlAssembler.assembleUrl(longUrl);
            return urlRepository.save(newlyCreatedUrl);
        });
        return url;
    }

    @Override
    @Transactional
    public void deleteUrl(String shortenedUrl) throws UrlNotFoundException {
        Url url = urlRepository.findByShortUrl(shortenedUrl).orElseThrow(() -> new UrlNotFoundException("URL was not found associated with provided short url"));
        urlRepository.deleteById(url.getId());
        return;
    }
}

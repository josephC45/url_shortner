package com.SpringBootApp.UrlShortner.service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
    public String getUrl(String shortUrl) throws UrlNotFoundException {
        Optional<Url> url = urlRepository.findByShortUrl(shortUrl);
        return url.map(Url::getLongUrl).orElseThrow(() -> new UrlNotFoundException("URL was not found associated with: " + shortUrl));
    }

    @Override
    public Url createUrl(String longUrl) {
        Optional<Url> url = urlRepository.findByLongUrl(longUrl);
        if(!url.isPresent()){
            Url newlyCreatedUrl = urlAssembler.assembleUrl(longUrl);
            return urlRepository.save(newlyCreatedUrl);
        }
        return url.get();
    }

    @Override
    public void deleteUrl(String shortenedUrl) throws UrlNotFoundException {
        Optional<Url> url = urlRepository.findByShortUrl(shortenedUrl);
        url.ifPresentOrElse(
            urlEntity -> {
                urlRepository.deleteById(urlEntity.getId());
            }, 
            () -> {
                throw new UrlNotFoundException("URL was not found with the short url of: " + shortenedUrl);
            }
        );
    }

}

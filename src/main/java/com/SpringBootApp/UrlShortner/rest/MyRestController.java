package com.SpringBootApp.UrlShortner.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.SpringBootApp.UrlShortner.dto.UrlDto;
import com.SpringBootApp.UrlShortner.entity.Url;
import com.SpringBootApp.UrlShortner.service.UrlService;

@RestController
@RequestMapping("/api/v1/urls")
public class MyRestController {

    private UrlService urlService;

    public MyRestController(UrlService urlService){
        this.urlService = urlService;
    }

    @GetMapping
    public ResponseEntity<UrlDto> getUrl(@RequestParam String shortUrl){
        String deserializedShortUrl = urlService.deserialize(shortUrl);
        UrlDto urlDto = urlService.getUrl(deserializedShortUrl);
        return new ResponseEntity<>(urlDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Url> createUrl(@RequestBody String longUrl, UriComponentsBuilder ucb) {
        String deserializedLongUrl = urlService.deserialize(longUrl);
        Url createdUrl = urlService.createUrl(deserializedLongUrl);
        UriComponents uriComponents = ucb.path("/api/v1/urls/{id}").buildAndExpand(createdUrl.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(createdUrl);
    }
       
    @DeleteMapping
    public ResponseEntity<Void> deleteUrl(@RequestParam String shortUrl){
        String deserializedShortUrl = urlService.deserialize(shortUrl);
        urlService.deleteUrl(deserializedShortUrl);
        return ResponseEntity.noContent().build();
    }
}

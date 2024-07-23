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
    public ResponseEntity<String> getUrl(@RequestParam String shortUrl){
        String decodedShortUrl = urlService.handleDecoding(shortUrl);
        String longUrl = urlService.getUrl(decodedShortUrl);
        return new ResponseEntity<>(longUrl, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Url> createUrl(@RequestBody String longUrl, UriComponentsBuilder ucb) {
        String decodedLongUrl = urlService.handleDecoding(longUrl);
        Url createdUrl = urlService.createUrl(decodedLongUrl);
        UriComponents uriComponents = ucb.path("/api/v1/urls/{id}").buildAndExpand(createdUrl.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(createdUrl);
    }
       
    @DeleteMapping
    public ResponseEntity<String> deleteUrl(@RequestParam String shortUrl){
        String decodedShortUrl = urlService.handleDecoding(shortUrl);
        urlService.deleteUrl(decodedShortUrl);
        return ResponseEntity.noContent().build();
    }
}

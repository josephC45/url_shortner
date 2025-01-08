package com.SpringBootApp.UrlShortner.rest;

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

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/urls")
public class MyRestController {

    private UrlService urlService;

    public MyRestController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping
    public Mono<ResponseEntity<UrlDto>> getUrl(@RequestParam String shortUrl) {
        return urlService.getUrl(shortUrl)
                .map(urlDto -> ResponseEntity.ok().body(urlDto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Url>> createUrl(@RequestBody String longUrl, UriComponentsBuilder ucb) {
        return urlService.createUrl(longUrl)
                .map(createdUrl -> {
                    UriComponents uriComponents = ucb.path("/api/v1/urls/{id}").buildAndExpand(createdUrl.getId());
                    return ResponseEntity.created(uriComponents.toUri()).body(createdUrl);
                });
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteUrl(@RequestParam String shortUrl) {
        return urlService.deleteUrl(shortUrl)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

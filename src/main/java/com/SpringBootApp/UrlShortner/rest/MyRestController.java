package com.SpringBootApp.UrlShortner.rest;

import java.util.concurrent.TimeUnit;

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

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/urls")
public class MyRestController {

    private UrlService urlService;
    private final Counter shortenUrlCounter;
    private final Timer shortenUrlLatency;

    public MyRestController(UrlService urlService, MeterRegistry meterRegistry) {
        this.urlService = urlService;
        this.shortenUrlCounter = meterRegistry.counter("url_shorten_requests_total");
        this.shortenUrlLatency = meterRegistry.timer("url_shorten_request_latency");
    }

    @GetMapping
    public Mono<ResponseEntity<UrlDto>> getUrl(@RequestParam String shortUrl) {
        return urlService.getUrl(shortUrl)
                .map(urlDto -> ResponseEntity.ok().body(urlDto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Mono<ResponseEntity<Url>> createUrl(@RequestBody String longUrl, UriComponentsBuilder ucb) {
        Mono<ResponseEntity<Url>> newUrl = null;
        long start = System.nanoTime();
        try {
            newUrl = urlService.createUrl(longUrl)
                    .map(createdUrl -> {
                        shortenUrlCounter.increment();
                        UriComponents uriComponents = ucb.path("/api/v1/urls/{id}").buildAndExpand(createdUrl.getId());
                        return ResponseEntity.created(uriComponents.toUri()).body(createdUrl);
                    });
        } finally {
            shortenUrlLatency.record(System.nanoTime() - start, TimeUnit.NANOSECONDS);
        }
        return newUrl;
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteUrl(@RequestParam String shortUrl) {
        return urlService.deleteUrl(shortUrl)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

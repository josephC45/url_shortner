package com.SpringBootApp.UrlShortner.rest;

import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.SpringBootApp.UrlShortner.dto.CreatedUrlDto;
import com.SpringBootApp.UrlShortner.dto.LongUrlDto;
import com.SpringBootApp.UrlShortner.dto.ShortUrlDto;
import com.SpringBootApp.UrlShortner.service.UrlService;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/urls")
public class MyRestController {

    private final UrlService urlService;
    private final Counter shortenUrlCounter;
    private final Timer shortenUrlLatency;

    public MyRestController(UrlService urlService, MeterRegistry meterRegistry) {
        this.urlService = urlService;
        this.shortenUrlCounter = meterRegistry.counter("url_shorten_requests_total");
        this.shortenUrlLatency = meterRegistry.timer("url_shorten_request_latency");
    }

    @GetMapping
    public Mono<ResponseEntity<LongUrlDto>> getUrl(@Valid @ModelAttribute ShortUrlDto shortUrlDto) {
        return urlService.getUrl(shortUrlDto)
                .map(urlDto -> ResponseEntity.ok().body(urlDto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<CreatedUrlDto>> createUrl(@RequestBody LongUrlDto longUrlDto, UriComponentsBuilder ucb) {
        Mono<ResponseEntity<CreatedUrlDto>> newUrl = null;
        long start = System.nanoTime();
        try {
            newUrl = urlService.createUrl(longUrlDto)
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
    public Mono<ResponseEntity<Void>> deleteUrl(@RequestHeader("X-User-Role") String role,
            @Valid @ModelAttribute ShortUrlDto shortUrlDto) {
        return (role.equals("ROLE_ADMIN")) ? 
            urlService.deleteUrl(shortUrlDto).then(Mono.just(ResponseEntity.noContent().build())) :
            Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
}

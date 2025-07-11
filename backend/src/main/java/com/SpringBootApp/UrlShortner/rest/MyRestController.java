package com.SpringBootApp.UrlShortner.rest;

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
import com.SpringBootApp.UrlShortner.monitoring.MonitoringService;
import com.SpringBootApp.UrlShortner.service.UrlService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/urls")
public class MyRestController {

    private final UrlService urlService;
    private final MonitoringService monitoringService;

    public MyRestController(UrlService urlService, MonitoringService monitoringService) {
        this.urlService = urlService;
        this.monitoringService = monitoringService;
    }

    @GetMapping
    public Mono<ResponseEntity<LongUrlDto>> getUrl(@Valid @ModelAttribute ShortUrlDto shortUrlDto) {
        return urlService.getUrl(shortUrlDto)
                .map(longUrlDto -> ResponseEntity.ok().body(longUrlDto));
    }

    @PostMapping
    public Mono<ResponseEntity<CreatedUrlDto>> createUrl(@RequestBody LongUrlDto longUrlDto, UriComponentsBuilder ucb) {
        return urlService.createUrl(longUrlDto)
            .map(createdUrl -> {
                UriComponents uriComponents = ucb.path("/api/v1/urls/{id}").buildAndExpand(createdUrl.getId());
                return ResponseEntity.created(uriComponents.toUri()).body(createdUrl);
            })
            .transform(mono -> monitoringService.trackLatency("app_create_url_latency", mono));
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteUrl(@RequestHeader("X-User-Role") String role,
            @Valid @ModelAttribute ShortUrlDto shortUrlDto) {
        if(!role.equals("ROLE_ADMIN")) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
        return urlService.deleteUrl(shortUrlDto).thenReturn(ResponseEntity.noContent().build());
    }
}

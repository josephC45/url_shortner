package com.personal_project.url_feed_service.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal_project.url_feed_service.dto.UrlResponseDto;
import com.personal_project.url_feed_service.service.FeedService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/feed/recent")
public class UrlFeedController {

    private final FeedService feedService;

    public UrlFeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public Flux<UrlResponseDto> getFeed() {
        return feedService.getLatestUrls();
    }

}

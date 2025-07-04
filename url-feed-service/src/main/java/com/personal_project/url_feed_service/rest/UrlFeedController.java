package com.personal_project.url_feed_service.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal_project.url_feed_service.dto.UrlResponseDto;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/feed/recent")
public class UrlFeedController {

    @Autowired
    ReactiveRedisTemplate<String, UrlResponseDto> reactiveRedisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlFeedController.class);

    @GetMapping
    public Flux<UrlResponseDto> getFeed() {
        LOGGER.info("Retrieving 10 latest URLs from cache");
        return reactiveRedisTemplate.opsForList()
            .range("recent_urls", 0, 9);
    }

}

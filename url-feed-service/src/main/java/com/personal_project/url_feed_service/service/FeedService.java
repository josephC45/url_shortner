package com.personal_project.url_feed_service.service;

import com.personal_project.url_feed_service.dto.UrlResponseDto;

import reactor.core.publisher.Flux;

public interface FeedService {

    public Flux<UrlResponseDto> getLatestUrls();

}

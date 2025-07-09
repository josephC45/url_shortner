package com.personal_project.url_feed_service.service;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import com.personal_project.url_feed_service.dto.UrlResponseDto;
import com.personal_project.url_feed_service.monitoring.MonitoringService;

import reactor.core.publisher.Flux;

@Service
public class FeedServiceImpl implements FeedService {

    private final ReactiveRedisTemplate<String, UrlResponseDto> reactiveRedisTemplate;
    private final MonitoringService monitoringService;

    public FeedServiceImpl(ReactiveRedisTemplate<String, UrlResponseDto> reactiveRedisTemplate,
            MonitoringService monitoringService) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
        this.monitoringService = monitoringService;
    }

    private Flux<UrlResponseDto> fetchFromRedis() {
        return reactiveRedisTemplate.opsForList().range("recent_urls", 0, 9);
    }

    @Override
    public Flux<UrlResponseDto> getLatestUrls() {
        return fetchFromRedis()
            .transform(flux -> monitoringService.trackLatency("app_redis_feed_latency", flux));
    }

}

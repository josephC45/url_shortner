package com.personal_project.url_kafka_consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal_project.url_kafka_consumer.dto.UrlRedisDto;
import com.personal_project.url_kafka_consumer.mapper.KafkaUrlToRedisUrlMapper;
import com.personal_project.url_kafka_consumer.wrapper.KafkaPayload;

@Service
public class UrlChangeConsumer {

    private final ObjectMapper objectMapper;
    private final KafkaUrlToRedisUrlMapper kafkaUrlToRedisUrlMapper;
    private final RedisTemplate<String, UrlRedisDto> redisTemplate;

    private static final String REDIS_KEY = "recent_urls";
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlChangeConsumer.class);

    public UrlChangeConsumer(ObjectMapper objectMapper, KafkaUrlToRedisUrlMapper kafkaUrlToRedisUrlMapper,
            RedisTemplate<String, UrlRedisDto> redisTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaUrlToRedisUrlMapper = kafkaUrlToRedisUrlMapper;
        this.redisTemplate = redisTemplate;
    }

    private UrlRedisDto parseKafkaMessage(String message) {
        try {
            KafkaPayload kafkaPayload = objectMapper.readValue(message, KafkaPayload.class);
            return kafkaUrlToRedisUrlMapper.toUrlRedisDto(kafkaPayload);
        } catch (JsonProcessingException e) {
            LOGGER.warn("Error deserializing message from Kafka", e.getMessage(), e);
            return null;
        }
    }

    private void cacheToRedis(UrlRedisDto urlRedisDto) {
        LOGGER.debug("Value being sent to redis: " + urlRedisDto.toString());
        redisTemplate.opsForList().leftPush(REDIS_KEY, urlRedisDto);
        redisTemplate.opsForList().trim(REDIS_KEY, 0, 9);
        LOGGER.debug("Redis trimmed to latest 10 URLs created");
    }

    @KafkaListener(topics = "dbserver1.public.urls", groupId = "url-feed-consumer-group")
    public void consume(String message) {
        UrlRedisDto urlForRedis = parseKafkaMessage(message);
        if(urlForRedis != null) cacheToRedis(urlForRedis);
        else LOGGER.warn("Skipping Redis push due to failed deserialization from Kafka");
    }

}

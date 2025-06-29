package com.personal_project.url_kafka_consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaUrlToRedisUrlMapper kafkaUrlToRedisUrlMapper;

    @Autowired
    private RedisTemplate<String, UrlRedisDto> redisTemplate;

    private static final String REDIS_KEY = "recent_urls";
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlChangeConsumer.class);

    @KafkaListener(topics = "dbserver1.public.urls", groupId = "url-feed-consumer-group")
    public void consume(String message) {
        try {
            KafkaPayload kafkaPayload = objectMapper.readValue(message, KafkaPayload.class);
            UrlRedisDto urlForRedis = kafkaUrlToRedisUrlMapper.toUrlRedisDto(kafkaPayload);

            LOGGER.info("Value being sent to redis: " + urlForRedis.toString());

            redisTemplate.opsForList().leftPush(REDIS_KEY, urlForRedis);
            redisTemplate.opsForList().trim(REDIS_KEY, 0, 9);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error deserializing message from Kafka", e.getMessage(), e);
        }
    }

}

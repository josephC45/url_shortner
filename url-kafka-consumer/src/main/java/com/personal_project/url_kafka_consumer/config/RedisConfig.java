package com.personal_project.url_kafka_consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal_project.url_kafka_consumer.dto.UrlRedisDto;

@Configuration
public class RedisConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    RedisTemplate<String, UrlRedisDto> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, UrlRedisDto> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Set serializers
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<UrlRedisDto> jsonSerializer = new Jackson2JsonRedisSerializer<>(UrlRedisDto.class);

        jsonSerializer.setObjectMapper(objectMapper);

        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(jsonSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(jsonSerializer);

        // Apply config
        template.afterPropertiesSet();

        return template;
    }

}

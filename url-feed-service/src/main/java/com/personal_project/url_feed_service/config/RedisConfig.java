package com.personal_project.url_feed_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal_project.url_feed_service.dto.UrlResponseDto;

@Configuration
public class RedisConfig {

        @Autowired
        private ObjectMapper objectMapper;

        @Bean
        ReactiveRedisTemplate<String, UrlResponseDto> reactiveRedisTemplate(
                        ReactiveRedisConnectionFactory connectionFactory) {
                Jackson2JsonRedisSerializer<UrlResponseDto> jacksonToJsonSerializer = new Jackson2JsonRedisSerializer<>(UrlResponseDto.class);
                jacksonToJsonSerializer.setObjectMapper(objectMapper); // deprecated

                RedisSerializationContext.RedisSerializationContextBuilder<String, UrlResponseDto> serializationContextBuilder = RedisSerializationContext
                        .newSerializationContext(new StringRedisSerializer());

                RedisSerializationContext<String, UrlResponseDto> serializationContext = serializationContextBuilder
                        .value(jacksonToJsonSerializer)
                        .build();

                return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);

        }

}

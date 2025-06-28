package com.personal_project.url_kafka_consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlRedisDto {

    private String shortUrl;
    private String longUrl;

}

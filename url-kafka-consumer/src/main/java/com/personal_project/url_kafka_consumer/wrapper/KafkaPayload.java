package com.personal_project.url_kafka_consumer.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KafkaPayload {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("url_hash")
    private String urlHash;

    @JsonProperty("short_url")
    private String shortUrl;

    @JsonProperty("long_url")
    private String longUrl;

}

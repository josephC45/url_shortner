package com.personal_project.url_kafka_consumer.dto;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlRedisDto {

    @NotEmpty
    @Size(max = 29)
    @URL
    private String shortUrl;
    
    @NotEmpty
    @Size(max = 2048)
    @URL
    private String longUrl;

}

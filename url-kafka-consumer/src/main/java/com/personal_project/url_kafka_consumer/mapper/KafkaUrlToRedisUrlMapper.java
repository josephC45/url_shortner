package com.personal_project.url_kafka_consumer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.personal_project.url_kafka_consumer.dto.UrlRedisDto;
import com.personal_project.url_kafka_consumer.wrapper.KafkaPayload;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KafkaUrlToRedisUrlMapper {
    UrlRedisDto toUrlRedisDto(KafkaPayload dto);
}

package com.SpringBootApp.UrlShortner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.SpringBootApp.UrlShortner.dto.CreatedUrlDto;
import com.SpringBootApp.UrlShortner.dto.LongUrlDto;
import com.SpringBootApp.UrlShortner.entity.Url;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UrlMapper {

    LongUrlDto toLongUrlDto(Url url);

    Url toEntity(LongUrlDto urlDto);

    CreatedUrlDto toCreatedUrlDto(Url url);

    Url toEntity(CreatedUrlDto createdUrlDto);
}

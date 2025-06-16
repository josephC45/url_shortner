package com.SpringBootApp.UrlShortner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.SpringBootApp.UrlShortner.dto.CreatedUrlDto;
import com.SpringBootApp.UrlShortner.dto.LongUrlDto;
import com.SpringBootApp.UrlShortner.entity.Url;

@Mapper(componentModel = "spring")
public interface UrlMapper {
    UrlMapper INSTANCE = Mappers.getMapper(UrlMapper.class);

    LongUrlDto toLongUrlDto(Url url);
    Url toEntity(LongUrlDto urlDto);

    CreatedUrlDto toCreatedUrlDto(Url url);
    Url toEntity(CreatedUrlDto createdUrlDto);
}

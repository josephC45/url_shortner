package com.SpringBootApp.UrlShortner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.SpringBootApp.UrlShortner.dto.AccountCreationRequestDto;
import com.SpringBootApp.UrlShortner.dto.LoginResponseDto;
import com.SpringBootApp.UrlShortner.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "roleType", ignore = true)
    User toUser(AccountCreationRequestDto accountCreationRequestDto);

    LoginResponseDto toLoginResponseDto(User user);

}

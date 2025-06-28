package com.SpringBootApp.UrlShortner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.SpringBootApp.UrlShortner.dto.AccountCreationRequestDto;
import com.SpringBootApp.UrlShortner.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toUser(AccountCreationRequestDto accountCreationRequestDto);

}

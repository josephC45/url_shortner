package com.SpringBootApp.UrlShortner.service;

import com.SpringBootApp.UrlShortner.dto.AccountCreationRequestDto;

import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<Boolean> registerUser(AccountCreationRequestDto accountCreationRequestDto);
}

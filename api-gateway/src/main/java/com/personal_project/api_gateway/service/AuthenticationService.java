package com.personal_project.api_gateway.service;

import org.springframework.http.server.reactive.ServerHttpResponse;

import com.personal_project.api_gateway.dto.AuthRequestDto;
import com.personal_project.api_gateway.dto.AuthResponseDto;

import reactor.core.publisher.Mono;

public interface AuthenticationService {

    public Mono<AuthResponseDto> authenticateUser(ServerHttpResponse response, AuthRequestDto authRequestDto);

}

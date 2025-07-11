package com.personal_project.api_gateway.service;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.personal_project.api_gateway.component.JwtService;
import com.personal_project.api_gateway.dto.AuthRequestDto;
import com.personal_project.api_gateway.dto.AuthResponseDto;

import reactor.core.publisher.Mono;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final ReactiveAuthenticationManager reactiveAuthenticationManager;
    private final JwtService jwtService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    public AuthenticationServiceImpl (
        @Qualifier("loginAuthenticationManager") ReactiveAuthenticationManager reactiveAuthenticationManager,
        JwtService jwtService) {
            this.reactiveAuthenticationManager = reactiveAuthenticationManager;
            this.jwtService = jwtService;
    }

    private Authentication getAuthenticationToken(AuthRequestDto authRequestDto) {
        return new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword());
    }

    private Mono<Authentication> authenticate(AuthRequestDto authRequestDto) {
        return reactiveAuthenticationManager.authenticate(getAuthenticationToken(authRequestDto));
    }

    private String createJWT(Authentication authenticatedUser) {
        String username = authenticatedUser.getName();
        String role = authenticatedUser.getAuthorities().stream()
            .findFirst()
            .map(GrantedAuthority::getAuthority)
            .orElse("ROLE_USER");

        return jwtService.generateToken(username, role);

    }

    private void constructAndAddResponseCookie(ServerHttpResponse response, String jwt) {
        ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
            .httpOnly(true)
            .secure(true) 
            .sameSite("None")
            .path("/")
            .maxAge(Duration.ofHours(1))
            .build();
        response.addCookie(cookie);
        LOGGER.debug("JWT added to response cookie");
    }

    private AuthResponseDto constructResponseDto(Authentication authenticatedUser) {
        String username = authenticatedUser.getName();
        return new AuthResponseDto(username);
    }

    @Override
    public Mono<AuthResponseDto> authenticateUser (ServerHttpResponse response, AuthRequestDto authRequestDto) {
        LOGGER.info("Authenticating user...");
        return authenticate(authRequestDto)
            .map(authenticatedUser -> {
                String jwt = createJWT(authenticatedUser);
                constructAndAddResponseCookie(response, jwt);
                LOGGER.info("User was successfully authenticated");
                return constructResponseDto(authenticatedUser);
            })
            .switchIfEmpty(Mono.error(new BadCredentialsException("Authentication failed")));
    }
}

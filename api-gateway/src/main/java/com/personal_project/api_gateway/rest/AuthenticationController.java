package com.personal_project.api_gateway.rest;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal_project.api_gateway.component.JwtService;
import com.personal_project.api_gateway.dto.AuthRequestDto;
import com.personal_project.api_gateway.dto.AuthResponseDto;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final ReactiveAuthenticationManager reactiveAuthenticationManager;
    private final JwtService jwtService;

    public AuthenticationController (
        @Qualifier("loginAuthenticationManager") ReactiveAuthenticationManager reactiveAuthenticationManager,
        JwtService jwtService) {
            this.reactiveAuthenticationManager = reactiveAuthenticationManager;
            this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponseDto>> login (ServerHttpResponse response, @RequestBody AuthRequestDto request){
        Authentication authToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        logger.info("Authenticating user...");
        return reactiveAuthenticationManager.authenticate(authToken)
            .flatMap(auth -> {
                String username = auth.getName();
                String role = auth.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("ROLE_USER");

                String jwt = jwtService.generateToken(username, role);

                ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                    .httpOnly(true)
                    .secure(true) 
                    .sameSite("None")
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .build();

                AuthResponseDto authResponseDto = new AuthResponseDto(username);
                response.addCookie(cookie);

                return Mono.fromRunnable(() -> logger.info("User was successfully authenticated"))
                    .thenReturn(
                        ResponseEntity.ok().body(authResponseDto)
                    );
            })
            .switchIfEmpty(
                Mono.fromRunnable(() -> logger.warn("Failiure to authenticate user"))
                .thenReturn(
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
                )
            );
                
    }
    
}

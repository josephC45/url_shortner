package com.personal_project.api_gateway.rest;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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

    private final ReactiveAuthenticationManager reactiveAuthenticationManager;
    private final JwtService jwtService;

    public AuthenticationController (
        @Qualifier("loginAuthenticationManager") ReactiveAuthenticationManager reactiveAuthenticationManager,
        JwtService jwtService) {
            this.reactiveAuthenticationManager = reactiveAuthenticationManager;
            this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponseDto>> login (@RequestBody AuthRequestDto request){
        Authentication authToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
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
                    .secure(false) // set to true after request is using https
                    .sameSite("Lax")
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .build();

                AuthResponseDto response = new AuthResponseDto(username, jwt);
                return Mono.just(
                    ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(response));
            })
            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
    
}

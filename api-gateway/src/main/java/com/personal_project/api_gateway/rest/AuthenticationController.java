package com.personal_project.api_gateway.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal_project.api_gateway.dto.AuthRequestDto;
import com.personal_project.api_gateway.dto.AuthResponseDto;
import com.personal_project.api_gateway.monitoring.MonitoringService;
import com.personal_project.api_gateway.service.AuthenticationService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final MonitoringService monitoringService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    public AuthenticationController (AuthenticationService authenticationService, MonitoringService monitoringService) {
        this.authenticationService = authenticationService;
        this.monitoringService = monitoringService;
    }

    @GetMapping("/status")
    public Mono<ResponseEntity<Void>> checkAuthStatus(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            LOGGER.info("Request was authenticated, check was successful");
            return Mono.just(ResponseEntity.ok().build());
        }
        LOGGER.warn("Request was unauthenticated, check was unsuccessful");
        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }


    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponseDto>> login (ServerHttpResponse response, @Valid @RequestBody AuthRequestDto request){
        return authenticationService.authenticateUser(response, request)
            .doOnSuccess(authResponse -> monitoringService.incrementSuccessfulLogins())
            .map(authResponse -> ResponseEntity.ok().body(authResponse))
            .onErrorResume(BadCredentialsException.class, ex -> {
                monitoringService.incrementUnSuccessfulLogins();
                LOGGER.warn("Authentication failed for email: {}", request.getEmail());
                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
            })
            .transform(mono -> monitoringService.trackLatency("app_login_latency", mono));
    }

    @PostMapping("/logout")
    public Mono<Void> logout(ServerHttpResponse response) {
        LOGGER.info("Logging user out");
        ResponseCookie expiredCookie = ResponseCookie.from("jwt", "")
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .path("/")
            .maxAge(0)
            .build();

        response.beforeCommit(() -> {
            response.addCookie(expiredCookie);
            return Mono.empty();
        });
        LOGGER.info("User successfully logged out");
        response.setStatusCode(HttpStatus.OK);
        return response.setComplete();
    }
    
}

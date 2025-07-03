package com.SpringBootApp.UrlShortner.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootApp.UrlShortner.dto.AccountCreationRequestDto;
import com.SpringBootApp.UrlShortner.service.AccountService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> createAccount(
            @Valid @RequestBody AccountCreationRequestDto accountCreationRequestDto) {
        return accountService.registerUser(accountCreationRequestDto)
                .flatMap(isNewAccount -> {
                    return isNewAccount ? 
                        Mono.fromRunnable(() -> LOGGER.info("Account successfully created"))
                        .thenReturn(ResponseEntity.ok("Account successfully created"))
                        : 
                        Mono.fromRunnable(() -> LOGGER.warn("Account was not created"))
                        .thenReturn(ResponseEntity.badRequest().body("Account was not created, it may exist already"));
                });
    }
}

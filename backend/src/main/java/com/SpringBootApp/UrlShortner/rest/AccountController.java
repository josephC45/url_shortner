package com.SpringBootApp.UrlShortner.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootApp.UrlShortner.dto.AccountCreationRequestDto;
import com.SpringBootApp.UrlShortner.service.AccountService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> createAccount(
            @RequestBody AccountCreationRequestDto accountCreationRequestDto) {
        return accountService.registerUser(accountCreationRequestDto)
                .map(isNewAccount -> {
                    return isNewAccount ? ResponseEntity.ok("Account successfully created")
                            : ResponseEntity.badRequest().body("Account was not created, it may exist already");
                });
    }
}

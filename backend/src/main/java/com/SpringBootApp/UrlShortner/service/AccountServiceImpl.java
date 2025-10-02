package com.SpringBootApp.UrlShortner.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SpringBootApp.UrlShortner.constant.Role;
import com.SpringBootApp.UrlShortner.dto.AccountCreationRequestDto;
import com.SpringBootApp.UrlShortner.entity.User;
import com.SpringBootApp.UrlShortner.mapper.UserMapper;
import com.SpringBootApp.UrlShortner.monitoring.MonitoringService;
import com.SpringBootApp.UrlShortner.repository.AccountRepository;

import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final MonitoringService monitoringService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl(PasswordEncoder passwordEncoder, AccountRepository accountRepository,
            UserMapper userMapper, MonitoringService monitoringService) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.userMapper = userMapper;
        this.monitoringService = monitoringService;
    }

    private Mono<Boolean> doesUserExist(AccountCreationRequestDto accountCreationRequestDto){
        return accountRepository.findByEmail(accountCreationRequestDto.getEmail())
            .map(existingUser -> passwordEncoder.matches(accountCreationRequestDto.getPassword(), existingUser.getPasswordHash()))
            .defaultIfEmpty(false);
    }

    private Mono<User> constructUser(AccountCreationRequestDto accountCreationRequestDto) {
        User user = userMapper.toUser(accountCreationRequestDto);
        user.setPasswordHash(passwordEncoder.encode(accountCreationRequestDto.getPassword()));
        user.setRoleType(Role.USER.name());
        return Mono.just(user);
    }

    private Mono<Boolean> saveUser(User user) {
        return accountRepository.save(user)
            .doOnSuccess(saved -> {
                LOGGER.info("Saved user: {}", saved);
                monitoringService.incrementTotalAccountsCreated();
            })
            .doOnError(error -> {
                LOGGER.error("Error performing save operation: {}", error);
            })
            .map(saved -> true)
            .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> registerUser(AccountCreationRequestDto accountCreationRequestDto) {
        return doesUserExist(accountCreationRequestDto)
            .flatMap(exists -> {
                if(exists) return Mono.just(false);
                return constructUser(accountCreationRequestDto).flatMap(this::saveUser);
            });
    }

}

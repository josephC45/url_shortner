package com.SpringBootApp.UrlShortner.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SpringBootApp.UrlShortner.constant.Role;
import com.SpringBootApp.UrlShortner.dto.AccountCreationRequestDto;
import com.SpringBootApp.UrlShortner.entity.User;
import com.SpringBootApp.UrlShortner.mapper.UserMapper;
import com.SpringBootApp.UrlShortner.repository.AccountRepository;

import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl(PasswordEncoder passwordEncoder, AccountRepository accountRepository,
            UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Mono<Boolean> registerUser(AccountCreationRequestDto accountCreationRequestDto) {
        return accountRepository.existsByEmail(accountCreationRequestDto.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.just(false);
                    }
                    if (!accountCreationRequestDto.getPassword()
                            .equals(accountCreationRequestDto.getVerifyPassword())) {
                        return Mono.just(false);
                    }
                    User user = userMapper.toUser(accountCreationRequestDto);
                    user.setPasswordHash(passwordEncoder.encode(accountCreationRequestDto.getPassword()));
                    user.setRoleType(Role.USER.name());
                    return accountRepository.save(user)
                            .doOnSuccess(saved -> logger.info("Saved user: " + saved))
                            .doOnError(error -> {
                                logger.info("Error during save: ", error.getMessage(), error);
                            })
                            .map(saved -> true)
                            .onErrorReturn(false);
                });
    }

}

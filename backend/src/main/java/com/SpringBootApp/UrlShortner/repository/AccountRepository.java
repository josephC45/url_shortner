package com.SpringBootApp.UrlShortner.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.SpringBootApp.UrlShortner.entity.User;

import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends R2dbcRepository<User, Long> {
    Mono<Boolean> existsByEmail(String email);

    Mono<User> findByEmail(String email);
}
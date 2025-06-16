package com.personal_project.api_gateway.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.personal_project.api_gateway.entity.AppUser;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<AppUser, Long> {
    
    Mono<AppUser> findByEmail(String email);

}

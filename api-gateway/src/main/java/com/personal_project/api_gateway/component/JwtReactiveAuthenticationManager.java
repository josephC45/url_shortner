package com.personal_project.api_gateway.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private JwtService jwtService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        try {
            Claims claims = jwtService.validateToken(token);
            String username = claims.getSubject();
            String role = claims.get("role", String.class);

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

            return Mono.just(new UsernamePasswordAuthenticationToken(username, token, authorities));
            
        } catch (BadCredentialsException e) {
            return Mono.error(e);
        }
    }

}

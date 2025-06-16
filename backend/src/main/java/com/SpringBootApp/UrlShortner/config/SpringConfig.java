package com.SpringBootApp.UrlShortner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
public class SpringConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .authorizeExchange(exchange ->
                exchange.pathMatchers(HttpMethod.DELETE, "/api/v1/urls/**")
                    .access((authentication, context) -> {
                        String role = context.getExchange().getRequest().getHeaders().getFirst("User-Role");
                        boolean isAdmin = "ROLE_ADMIN".equals(role);
                        return Mono.just(new AuthorizationDecision(isAdmin));
                    })
                .anyExchange().permitAll()
            )
            .build();
    }
    
}

package com.personal_project.api_gateway.component;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class AddRoleHeaderFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        return ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication)
            .flatMap(auth -> {
                if(auth == null || !auth.isAuthenticated()) {
                    return webFilterChain.filter(serverWebExchange);
                }
                String role = auth.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("ROLE_NONE");
                    
                ServerHttpRequest mutatedRequest = serverWebExchange.getRequest().mutate()
                    .header("X-User-Role", role)
                    .build();

                ServerWebExchange mutatedExchange = serverWebExchange.mutate().request(mutatedRequest).build();
                return webFilterChain.filter(mutatedExchange);
            })
            .switchIfEmpty(webFilterChain.filter(serverWebExchange));
    }

}

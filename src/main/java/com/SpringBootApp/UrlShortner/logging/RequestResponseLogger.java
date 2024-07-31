package com.SpringBootApp.UrlShortner.logging;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestResponseLogger extends OncePerRequestFilter{

    private static final Logger LOGGER = LogManager.getLogger(RequestResponseLogger.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info("Incoming Request: method={} uri={}", request.getMethod(), request.getRequestURI());
        filterChain.doFilter(request, response);
        LOGGER.info("Outgoing response: status {}", response.getStatus());
    }

}

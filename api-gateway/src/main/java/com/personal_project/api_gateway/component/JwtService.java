package com.personal_project.api_gateway.component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {

    private final SecretKey secretKey;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);

    public JwtService(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        LOGGER.debug("Generating JWT");
        return createToken(username, claims);
    }

    private String createToken(String username, Map<String, Object> claims) {
        long expirationMillis = 1000 * 60 * 60; // 1hr
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validateToken(String token) throws BadCredentialsException {
        LOGGER.debug("Validating JWT");
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (JwtException e) {
            LOGGER.error("Invalid JWT");
            throw new BadCredentialsException("Invalid JWT", e);
        }
    }

}

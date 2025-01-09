package com.SpringBootApp.UrlShortner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.SpringBootApp.UrlShortner.dto.ErrorDto;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public Mono<ResponseEntity<ErrorDto>> handleUrlNotFoundException(UrlNotFoundException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(ex.getMessage())));
    }
}

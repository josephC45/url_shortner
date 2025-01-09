package com.SpringBootApp.UrlShortner.UnitTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.SpringBootApp.UrlShortner.dto.ErrorDto;
import com.SpringBootApp.UrlShortner.exception.GlobalExceptionHandler;
import com.SpringBootApp.UrlShortner.exception.UrlNotFoundException;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ExceptionHandlingTests {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setup() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @DisplayName("should return appropriate status code and exception message when UrlNotFoundException is thrown")
    @Test
    public void shouldReturnNotFoundStatusAndMessageWhenUrlNotFoundExceptionIsThrownForGetUrl() {
        String exceptionMessage = "URL was not found associated with provided short url";
        UrlNotFoundException urlNotFoundException = new UrlNotFoundException(exceptionMessage);
        Mono<ResponseEntity<ErrorDto>> globalException = globalExceptionHandler.handleUrlNotFoundException(urlNotFoundException);
        StepVerifier.create(globalException)
                .assertNext(exceptionResponse-> {
                    assertEquals(HttpStatus.NOT_FOUND, exceptionResponse.getStatusCode());
                    assertTrue(exceptionResponse.getBody() instanceof ErrorDto);
                    ErrorDto exceptionResponseMsg = exceptionResponse.getBody();
                    assertEquals(exceptionMessage, exceptionResponseMsg.getErrorMessage());
                }).verifyComplete();
    }
}

package com.SpringBootApp.UrlShortner.UnitTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.SpringBootApp.UrlShortner.dto.ErrorDto;
import com.SpringBootApp.UrlShortner.exception.GlobalExceptionHandler;
import com.SpringBootApp.UrlShortner.exception.UrlNotFoundException;
import com.SpringBootApp.UrlShortner.rest.MyRestController;
import com.SpringBootApp.UrlShortner.service.UrlService;

@ExtendWith(MockitoExtension.class)
public class ExceptionHandlingTests {
    
    @Mock
    private UrlService urlService;

    @InjectMocks
    private MyRestController restController;

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setup() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @DisplayName("should return appropriate status code and exception message when UrlNotFoundException is thrown for GET endpoint")
    @Test
    public void shouldReturnNotFoundStatusAndMessageWhenUrlNotFoundExceptionIsThrownForGetUrl() {
        String shortUrl = "shortUrl";
        String deserializedUrl = "deserializedUrl";
        String exceptionMessage = "URL was not found associated with provided short url";

        when(urlService.deserialize(shortUrl)).thenReturn(deserializedUrl);
        when(urlService.getUrl(deserializedUrl)).thenThrow(new UrlNotFoundException(exceptionMessage));

        ResponseEntity<?> response;
        try {
            response = restController.getUrl(shortUrl);
        } catch (UrlNotFoundException ex) {
            response = globalExceptionHandler.handleUrlNotFoundException(ex);
        }

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof  ErrorDto);
        ErrorDto responseMessage = (ErrorDto) response.getBody();
        assertEquals(exceptionMessage, responseMessage.getErrorMessage());
    }

    @DisplayName("should return appropriate status code and exception message when UrlNotFoundException is thrown for DELETE endpoint")
    @Test
    public void shouldReturnNotFoundStatusAndMessageWhenUrlNotFoundExceptionIsThrownForDeleteUrl() {
        String shortUrl = "shortUrl";
        String deserializedUrl = "deserializedUrl";
        String exceptionMessage = "URL was not found associated with provided short url";

        when(urlService.deserialize(shortUrl)).thenReturn(deserializedUrl);
        doThrow(new UrlNotFoundException(exceptionMessage)).when(urlService).deleteUrl(deserializedUrl);

        ResponseEntity<?> response;
        try {
            response = restController.deleteUrl(shortUrl);
        } catch (UrlNotFoundException ex) {
            response = globalExceptionHandler.handleUrlNotFoundException(ex);
        }

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof  ErrorDto);
        ErrorDto responseMessage = (ErrorDto) response.getBody();
        assertEquals(exceptionMessage, responseMessage.getErrorMessage());
    }

}

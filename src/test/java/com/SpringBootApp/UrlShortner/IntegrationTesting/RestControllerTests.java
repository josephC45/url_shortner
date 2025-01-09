package com.SpringBootApp.UrlShortner.IntegrationTesting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.SpringBootApp.UrlShortner.dto.UrlDto;
import com.SpringBootApp.UrlShortner.entity.Url;
import com.SpringBootApp.UrlShortner.repository.UrlRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UrlRepository urlRepository;

    @BeforeEach
    public void setup() {
        urlRepository.deleteAll().block();
    }

    @Test
    void shouldReturnOkStatusCodeAndLongUrl_WhenGivenJsonValueOfShortUrl() {
        String urlRequest = "http://localhost:8080/abcdefg1234567";
        Url createdUrl = webTestClient.post()
                .uri("/api/v1/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(urlRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Url.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdUrl);
        assertNotNull(createdUrl.getShortUrl());

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/urls")
                        .queryParam("shortUrl", createdUrl.getShortUrl())
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UrlDto.class)
                .consumeWith(response -> {
                    UrlDto responseBody = response.getResponseBody();
                    assertNotNull(responseBody);
                    assertEquals(urlRequest, responseBody.getUrl());
                });

    }

    @Test
    void shouldReturnNoContentStatusCode_WhenGivenJsonValueOfShortUrlToDelete() {
        String urlRequest = "http://localhost:8080/abcdefg1234567";
        Url createdUrl = webTestClient.post()
                .uri("/api/v1/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(urlRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Url.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdUrl);
        assertNotNull(createdUrl.getShortUrl());

        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/urls")
                        .queryParam("shortUrl", createdUrl.getShortUrl())
                        .build())
                .exchange()
                .expectStatus().isNoContent();

        Optional<Url> deletedUrl = urlRepository.findByShortUrl(createdUrl.getShortUrl()).blockOptional();
        assertTrue(deletedUrl.isEmpty());
    }

    @Test
    void shouldReturnCreatedStatusAndBody_WhenGivenJsonValueOfLongUrl() {
        String urlRequest = "http://localhost:8080/abcdefg1234567";
        Url createdUrl = webTestClient.post()
                .uri("/api/v1/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(urlRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Url.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdUrl);
        assertNotNull(createdUrl.getUrlHash());
        assertNotNull(createdUrl.getShortUrl());
        assertNotNull(createdUrl.getLongUrl());
        assertEquals(urlRequest, createdUrl.getLongUrl());
    }
}

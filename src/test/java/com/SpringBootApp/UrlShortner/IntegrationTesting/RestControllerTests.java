package com.SpringBootApp.UrlShortner.IntegrationTesting;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBootApp.UrlShortner.dto.UrlDto;
import com.SpringBootApp.UrlShortner.entity.Url;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class RestControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

    private static HttpHeaders headers;
    private static String serializedUrl;

    @BeforeAll
    public static void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        serializedUrl = URLEncoder.encode("http://localhost:8080/abcdefg1234567", StandardCharsets.UTF_8);
    }

    @Test
    void shouldReturnNoContentStatusCodeAndLongUrl_WhenGivenJsonValueOfShortUrl() {
        HttpEntity<String> creationRequest = new HttpEntity<String>(serializedUrl, headers);
        ResponseEntity<Url> responseOfNewlyCreatedUrlObj = restTemplate
        .postForEntity("/api/v1/urls", creationRequest, Url.class);

        String expectedLongUrl = responseOfNewlyCreatedUrlObj.getBody().getLongUrl();
        String shortUrl = responseOfNewlyCreatedUrlObj.getBody().getShortUrl();
        String serializedShortUrl = URLEncoder.encode(shortUrl, StandardCharsets.UTF_8);

        ResponseEntity<?> response = restTemplate
        .getForEntity("/api/v1/urls?shortUrl={shortUrl}", UrlDto.class, serializedShortUrl);

        assertTrue(response.getBody() instanceof UrlDto);
        UrlDto responseMessage = (UrlDto) response.getBody();
        assertEquals(expectedLongUrl, responseMessage.getUrl());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void shouldReturnNoContentStatusCode_WhenGivenJsonValueOfShortUrlToDelete() {
        HttpEntity<String> creationRequest = new HttpEntity<String>(serializedUrl, headers);
        ResponseEntity<Url> responseOfNewlyCreatedUrlObj = restTemplate
        .postForEntity("/api/v1/urls", creationRequest, Url.class);

        String shortUrl = responseOfNewlyCreatedUrlObj.getBody().getShortUrl().toString();
        String serializedShortUrl = URLEncoder.encode(shortUrl, StandardCharsets.UTF_8);

        HttpEntity<String> deletionRequest = new HttpEntity<String>(headers);
        ResponseEntity<Void> responseOfDeletingAboveUrlUsingShortUrl = restTemplate
        .exchange("/api/v1/urls?shortUrl={shortUrl}", HttpMethod.DELETE, deletionRequest, Void.class, serializedShortUrl);

        assertEquals(responseOfDeletingAboveUrlUsingShortUrl.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldReturnCreatedStatusAndBody_WhenGivenJsonValueOfLongUrl() {
        HttpEntity<String> request = new HttpEntity<String>(serializedUrl,headers);

        ResponseEntity<Url> response = restTemplate
        .postForEntity("/api/v1/urls", request, Url.class);

        String deserializedLongUrl = URLDecoder.decode(serializedUrl, StandardCharsets.UTF_8);
        
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getLongUrl(), deserializedLongUrl);
        assertNotNull(response.getBody().getUrlHash());
        assertNotNull(response.getBody().getShortUrl());
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }
}

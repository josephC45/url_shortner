package com.SpringBootApp.UrlShortner.EndToEndTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UrlShortenerE2ETests {

    @Test
    public void shouldCreateShortenedUrlAndRetrieveLongUrl() {
        RestAssured.baseURI = "http://localhost:8080";

        String longUrl = "http://localhost:8080/abcdefg1234567";
        Response response = RestAssured
            .given()
            .contentType("application/json")
            .body(longUrl)
            .post("/api/v1/urls");
        assertEquals(201, response.getStatusCode());
        String shortUrl = response.jsonPath().getString("shortUrl");
        assertNotNull(shortUrl);

        Response retrieveLongUrlFromShortUrl = RestAssured
            .given()
            .queryParam("shortUrl", shortUrl)
            .get("/api/v1/urls");
        assertEquals(200,retrieveLongUrlFromShortUrl.getStatusCode());
        String longUrlAssociatedWithNewlyCreatedShortUrl = retrieveLongUrlFromShortUrl.jsonPath().getString("url");
        assertEquals(longUrlAssociatedWithNewlyCreatedShortUrl, longUrl);
    }

    @Test
    public void shouldCreateShortenedUrlObjectAndDeleteSameUrlObject() {
        RestAssured.baseURI = "http://localhost:8080";

        String longUrl = "http://localhost:8080/abcdefg12345678";
        Response creationResponse = RestAssured
            .given()
            .contentType("application/json")
            .body(longUrl)
            .post("/api/v1/urls");

        assertEquals(201, creationResponse.getStatusCode());
        String shortUrl = creationResponse.jsonPath().getString("shortUrl");
        assertNotNull(shortUrl);

        Response deletionResponse = RestAssured
            .given()
            .queryParam("shortUrl", shortUrl)
            .delete("/api/v1/urls");
        assertEquals(204, deletionResponse.getStatusCode());

        Response getUrlThatDoesNotExistResponse = RestAssured
            .given()
            .queryParam("shortUrl", shortUrl)
            .get("/api/v1/urls");
        assertEquals(404, getUrlThatDoesNotExistResponse.getStatusCode());
    }
}

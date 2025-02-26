package com.SpringBootApp.UrlShortner.UnitTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.SpringBootApp.UrlShortner.config.ConfigProp;
import com.SpringBootApp.UrlShortner.entity.Url;
import com.SpringBootApp.UrlShortner.service.KeyCreatorImpl;
import com.SpringBootApp.UrlShortner.service.UrlAssemblerImpl;

@ExtendWith(MockitoExtension.class)
public class UrlAssemblerTest {

    @Mock
    ConfigProp configProp;
    
    @Mock
    KeyCreatorImpl keyCreator;

    @InjectMocks
    UrlAssemblerImpl urlAssembler;

    @DisplayName("assembleUrl should create a new Url object with expected properties")
    @Test
    public void shouldCreateNewUrlObjectWithExpectedProperties(){
        //arrange
        when(keyCreator.createKey()).thenReturn("7chd9s1");
        when(configProp.getShortUrlBase()).thenReturn("http://localhost:8080/");

        Url expectedUrl = new Url("7chd9s1", "http://localhost:8080/7chd9s1", "http://localhost:8080/abc1234567");
        
        //act
        Url actual = urlAssembler.assembleUrl("http://localhost:8080/abc1234567");

        //assert
        assertEquals(expectedUrl.getUrlHash(), actual.getUrlHash(), "Expected url and actual url should match");
        assertEquals(expectedUrl.getShortUrl(), actual.getShortUrl(), "Expected shorturl and actual shorturl should match");
        assertEquals(expectedUrl.getLongUrl(), actual.getLongUrl(), "Expected longurl and shorturl should match");
    }
}

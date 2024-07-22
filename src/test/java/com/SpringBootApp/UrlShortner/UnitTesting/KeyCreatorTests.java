package com.SpringBootApp.UrlShortner.UnitTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.SpringBootApp.UrlShortner.config.ConfigProp;
import com.SpringBootApp.UrlShortner.service.KeyCreatorImpl;

@ExtendWith(MockitoExtension.class)
public class KeyCreatorTests {

    @Mock
    private ConfigProp configProp;

    @InjectMocks
    private KeyCreatorImpl keyCreator;

    @DisplayName("should create a alphanumeric string with a length of 7")
    @Test
    public void shouldReturnAlphaNumericHashOfLengthSeven(){
        //arrange
        when(configProp.getBaseConversion()).thenReturn(62);
        String regex = "^[a-zA-Z0-9]*$";

        //act
        String createdKey = keyCreator.createKey();

        //assert
        assertNotNull(createdKey, "Key should not be null");
        assertFalse(createdKey.isEmpty(), "Key should not be empty");
        assertTrue(createdKey.matches(regex), "Key should match the regex");
        assertEquals(7, createdKey.length(), "Key should have a length of 7");
    }
}

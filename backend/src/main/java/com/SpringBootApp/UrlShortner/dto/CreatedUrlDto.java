package com.SpringBootApp.UrlShortner.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatedUrlDto {

    private int id;

    @NotEmpty
    @Size(max = 29)
    @URL
    private String shortUrl;

    @NotEmpty
    @Size(max = 2048)
    @URL
    private String longUrl;

}

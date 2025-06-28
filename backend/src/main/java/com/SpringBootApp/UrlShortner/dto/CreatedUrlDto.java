package com.SpringBootApp.UrlShortner.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatedUrlDto {

    private int id;
    private String shortUrl;
    private String longUrl;

}

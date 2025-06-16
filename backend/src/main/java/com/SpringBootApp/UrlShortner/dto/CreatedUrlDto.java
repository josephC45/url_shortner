package com.SpringBootApp.UrlShortner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatedUrlDto {
    
    private int id;
    private String shortUrl;
    private String longUrl;

    public CreatedUrlDto(int id, String shortUrl, String longUrl){
        this.id = id;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }
    
}

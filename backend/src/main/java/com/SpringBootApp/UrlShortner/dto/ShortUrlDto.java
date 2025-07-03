package com.SpringBootApp.UrlShortner.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlDto {

    @NotEmpty
    public String shortUrl;
    
}

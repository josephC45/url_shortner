package com.SpringBootApp.UrlShortner.dto;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlDto {

    @NotEmpty
    @Size(max = 29)
    @URL
    public String shortUrl;
    
}

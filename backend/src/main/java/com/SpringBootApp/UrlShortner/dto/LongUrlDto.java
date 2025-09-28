package com.SpringBootApp.UrlShortner.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LongUrlDto {

    @NotEmpty
    @Size(max = 2048)
    @URL
    private String longUrl;

}

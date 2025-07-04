package com.SpringBootApp.UrlShortner.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LongUrlDto {

    @NotNull
    private String longUrl;

}

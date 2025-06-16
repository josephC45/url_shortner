package com.SpringBootApp.UrlShortner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LongUrlDto {

    @NonNull
    private String longUrl;

}

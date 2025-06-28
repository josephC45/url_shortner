package com.SpringBootApp.UrlShortner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LongUrlDto {

    @NonNull
    private String longUrl;

}

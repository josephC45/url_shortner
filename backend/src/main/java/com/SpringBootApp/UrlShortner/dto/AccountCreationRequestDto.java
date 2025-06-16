package com.SpringBootApp.UrlShortner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountCreationRequestDto {

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String verifyPassword;
}

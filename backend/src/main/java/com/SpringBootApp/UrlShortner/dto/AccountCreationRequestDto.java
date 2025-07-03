package com.SpringBootApp.UrlShortner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreationRequestDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;

    @NotEmpty
    private String verifyPassword;
}

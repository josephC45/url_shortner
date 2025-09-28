package com.SpringBootApp.UrlShortner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreationRequestDto {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 16, max = 32)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_+=:?])[A-Za-z0-9!@#$%^&*_+=:?]{16,32}$")
    private String password;

    @NotEmpty
    @Size(min = 16, max = 32)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_+=:?])[A-Za-z0-9!@#$%^&*_+=:?]{16,32}$")
    private String verifyPassword;
}

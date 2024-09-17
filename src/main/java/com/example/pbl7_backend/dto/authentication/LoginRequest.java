package com.example.pbl7_backend.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Invalid username")
    private String username;
    @NotBlank(message = "Invalid password")
    private String password;
}

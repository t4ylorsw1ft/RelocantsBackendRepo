package org.example.relocantsbackend.dto.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginDTO {

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    public String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    public String password;
}

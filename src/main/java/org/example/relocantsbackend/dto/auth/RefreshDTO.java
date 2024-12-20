package org.example.relocantsbackend.dto.auth;

import jakarta.validation.constraints.NotEmpty;

public class RefreshDTO {
    @NotEmpty(message = "Refresh token is required")
    public String refreshToken;
}

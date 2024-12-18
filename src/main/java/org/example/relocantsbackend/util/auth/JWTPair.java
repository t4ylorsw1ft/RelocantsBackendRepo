package org.example.relocantsbackend.util.auth;

public class JWTPair {
    private String AccessToken;
    private String RefreshToken;

    public JWTPair(String accessToken, String refreshToken) {
        AccessToken = accessToken;
        RefreshToken = refreshToken;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public String getRefreshToken() {
        return RefreshToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        RefreshToken = refreshToken;
    }
}

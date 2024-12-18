package org.example.relocantsbackend.util.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    private final String secretKey;
    private final Algorithm algorithm;
    private final int accessTokenExpiresHours;
    private final int refreshTokenExpiresDays;

    public JwtProvider() {
        // Настройки задаются здесь
        this.secretKey = "LoadUpProToolsAndPressThreeStudioFilledWithJackboxAndPepsis";
        this.algorithm = Algorithm.HMAC256(secretKey);
        this.accessTokenExpiresHours = 2;
        this.refreshTokenExpiresDays = 30;
    }

    public String generateRefreshToken() {
        byte[] randomBytes = new byte[64];
        new SecureRandom().nextBytes(randomBytes);
        String randomValue = Base64.getEncoder().encodeToString(randomBytes);

        return JWT.create()
                .withClaim("body", randomValue)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpiresDays * 24L * 60 * 60 * 1000))
                .sign(algorithm);
    }

    public String generateAccessToken(int userId) {
        return JWT.create()
                .withClaim("userId", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiresHours * 60L * 60 * 1000))
                .sign(algorithm);
    }

    public DecodedJWT verifyToken(String token) {
        return JWT.require(algorithm)
                .build()
                .verify(token);
    }

    public int getTokenUserId(String token) {
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getClaim("userId")
                .asInt();
    }

    public boolean validateToken(String token) {
        try {
            verifyToken(token);
            return true;
        } catch (TokenExpiredException e) {
            System.err.println("Token expired: " + e.getMessage());
        } catch (SignatureVerificationException e) {
            System.err.println("Invalid signature: " + e.getMessage());
        } catch (JWTDecodeException e) {
            System.err.println("Token decoding error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Token validation failed: " + e.getMessage());
        }
        return false;
    }
}

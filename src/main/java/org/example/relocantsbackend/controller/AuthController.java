package org.example.relocantsbackend.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.example.relocantsbackend.dto.auth.LoginDTO;
import org.example.relocantsbackend.dto.auth.RefreshDTO;
import org.example.relocantsbackend.dto.auth.RegisterUserDTO;
import org.example.relocantsbackend.entity.User;
import org.example.relocantsbackend.exception.NotFoundException;
import org.example.relocantsbackend.service.UserService;
import org.example.relocantsbackend.util.auth.JWTPair;
import org.example.relocantsbackend.util.auth.JwtProvider;
import org.example.relocantsbackend.util.auth.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.ConsoleHandler;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/public/register")
    public ResponseEntity<JWTPair> Register(@Valid @RequestBody RegisterUserDTO userDto) {
        PasswordHasher hasher = new PasswordHasher();
        JwtProvider jwtProvider = new JwtProvider();

        String passwordHash = hasher.generate(userDto.getPassword());
        LocalDateTime dateTime = LocalDateTime.now();

        User user = new User(userDto.getEmail(), passwordHash, userDto.getUsername(), null, dateTime);
        userService.saveUser(user);

        String accessToken = jwtProvider.generateAccessToken(user.getId());;
        String refreshToken = refreshToken = jwtProvider.generateRefreshToken();
        var jwtPair = new JWTPair(accessToken, refreshToken);

        user.setRefreshToken(refreshToken);
        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(jwtPair);
    }

    @PostMapping("/public/login")
    public ResponseEntity<JWTPair> Login(@Valid @RequestBody LoginDTO loginDTO) throws LoginException {
        PasswordHasher hasher = new PasswordHasher();
        JwtProvider jwtProvider = new JwtProvider();

        User user = userService.getUserByEmail(loginDTO.email);
        if(user == null) {
            throw new NotFoundException("User", loginDTO.email);
        }

        String passwordHash = user.getPasswordHash();

        if(hasher.verify(loginDTO.password, passwordHash)) {
            String accessToken = jwtProvider.generateAccessToken(user.getId());
            String refreshToken = jwtProvider.generateRefreshToken();
            JWTPair jwtPair = new JWTPair(accessToken, refreshToken);
            user.setRefreshToken(refreshToken);
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(jwtPair);
        }
        else {
            throw new LoginException();
        }
    }

    @PostMapping("/public/refresh")
    public ResponseEntity<JWTPair> Refresh(@Valid @RequestBody RefreshDTO refreshDTO) {
        JwtProvider jwtProvider = new JwtProvider();
        var refreshToken = refreshDTO.refreshToken;

        User user = userService.getUserByRefreshToken(refreshToken);

        if(user == null) {
            throw new NotFoundException("User", refreshToken);
        }

        if(new Date().before(jwtProvider.verifyToken(user.getRefreshToken()).getExpiresAt())){
            String newAccessToken = jwtProvider.generateAccessToken(user.getId());
            String newRefreshToken = jwtProvider.generateRefreshToken();
            JWTPair jwtPair = new JWTPair(newAccessToken, newRefreshToken);
            user.setRefreshToken(newRefreshToken);
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(jwtPair);
        }
        else {
            throw new ValidationException("Invalid refresh token");
        }
    }


}

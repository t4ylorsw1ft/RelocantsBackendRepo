package org.example.relocantsbackend.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.example.relocantsbackend.dto.auth.LoginDTO;
import org.example.relocantsbackend.dto.auth.RegisterUserDTO;
import org.example.relocantsbackend.entity.User;
import org.example.relocantsbackend.exception.NotFoundException;
import org.example.relocantsbackend.service.UserService;
import org.example.relocantsbackend.util.auth.JwtProvider;
import org.example.relocantsbackend.util.auth.PasswordHasher;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService, EmailValidator emailValidator) {
        this.userService = userService;
    }

    @PostMapping("/public/register")
    public ResponseEntity<User> Register(@Valid @RequestBody RegisterUserDTO userDto) {
        PasswordHasher hasher = new PasswordHasher();

        String passwordHash = hasher.generate(userDto.getPassword());
        LocalDateTime dateTime = LocalDateTime.now();

        User user = new User(userDto.getEmail(), passwordHash, userDto.getUsername(), null, dateTime);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userService.saveUser(user));
    }

    @PostMapping("/public/login")
    public ResponseEntity<String> Login(@Valid @RequestBody LoginDTO loginDTO) throws LoginException {
        PasswordHasher hasher = new PasswordHasher();
        JwtProvider jwtProvider = new JwtProvider();

        User user = userService.getUserByEmail(loginDTO.email);
        if(user == null) {
            throw new NotFoundException("User", loginDTO.email);
        }

        String passwordHash = user.getPasswordHash();

        String accessToken = null;
        String refreshToken;

        if(hasher.verify(loginDTO.password, passwordHash)) {
            accessToken = jwtProvider.generateAccessToken(user.getId());
            refreshToken = jwtProvider.generateRefreshToken();
            user.setRefreshToken(refreshToken);
            userService.saveUser(user);
        }
        else {
            throw new LoginException();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(accessToken);
    }

    @PostMapping("/public/refresh")
    public ResponseEntity<String> Refresh(@RequestBody String refreshToken) {
        JwtProvider jwtProvider = new JwtProvider();

        User user = userService.getUserByRefreshToken(refreshToken);
        if(user == null) {
            throw new NotFoundException("User", refreshToken);
        }

        String newAccessToken = null;
        String newRefreshToken;

        if(new Date().before(jwtProvider.verifyToken(user.getRefreshToken()).getExpiresAt())){
            newAccessToken = jwtProvider.generateAccessToken(user.getId());
            newRefreshToken = jwtProvider.generateRefreshToken();
            user.setRefreshToken(newRefreshToken);
            userService.saveUser(user);
        }
        else {
            throw new ValidationException("Invalid refresh token");
        }

        return ResponseEntity.status(HttpStatus.OK).body(newAccessToken);
    }


}

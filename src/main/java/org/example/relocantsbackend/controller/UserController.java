package org.example.relocantsbackend.controller;

import org.example.relocantsbackend.dto.users.UserDTO;
import org.example.relocantsbackend.entity.User;
import org.example.relocantsbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Получение информации о себе
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUserMe() {
        // Получение текущей аутентификации из SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("bbbbbbbbbb");
        // Проверка, что аутентификация существует и является корректной
        if (authentication == null || !(authentication.getPrincipal() instanceof Integer)) {
            System.out.println("aaaaaaaaaa");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Integer userId = (Integer) authentication.getPrincipal();
        Optional<User> user = userService.getUserById(userId);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Преобразуем User в UserDTO
        User userEntity = user.get();
        UserDTO userDTO = new UserDTO(userEntity.getId(), userEntity.getEmail(), userEntity.getFullName(), userEntity.getAvatarUrl(), userEntity.getRegistrationDate());

        return ResponseEntity.ok(userDTO);
    }

    // Получение пользователя по ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("bbbbbbbbbb");
        // Проверка, что аутентификация существует и является корректной
        if (authentication == null || !(authentication.getPrincipal() instanceof Integer)) {
            System.out.println("aaaaaaaaaa");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<User> user = userService.getUserById(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Преобразуем User в UserDTO
        User userEntity = user.get();
        UserDTO userDTO = new UserDTO(userEntity.getId(), userEntity.getEmail(), userEntity.getFullName(), userEntity.getAvatarUrl(), userEntity.getRegistrationDate());

        return ResponseEntity.ok(userDTO);
    }

    // Получение пользователя по email
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    // Получение всех пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Создание или обновление пользователя
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // Удаление пользователя по ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    // Проверка существования пользователя по ID
    @GetMapping("/exists/{id}")
    public boolean userExists(@PathVariable int id) {
        return userService.userExists(id);
    }

    // Получение количества пользователей
    @GetMapping("/count")
    public long getUserCount() {
        return userService.getUserCount();
    }
}

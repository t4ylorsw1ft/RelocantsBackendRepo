package org.example.relocantsbackend.controller;

import org.example.relocantsbackend.entity.User;
import org.example.relocantsbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    // Получение пользователя по ID
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
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

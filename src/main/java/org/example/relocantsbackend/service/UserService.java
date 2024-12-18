package org.example.relocantsbackend.service;

import org.example.relocantsbackend.entity.User;
import org.example.relocantsbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Получение пользователя по ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // Получение пользователя по email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserByRefreshToken (String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken);
    }

    // Получение всех пользователей
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Создание или обновление пользователя
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Удаление пользователя по ID
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    // Проверка существования пользователя по ID
    public boolean userExists(int id) {
        return userRepository.existsById(id);
    }

    // Получение количества пользователей
    public long getUserCount() {
        return userRepository.count();
    }
}

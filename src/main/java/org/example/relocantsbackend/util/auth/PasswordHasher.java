package org.example.relocantsbackend.util.auth;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    // Генерация хэша пароля
    public String generate(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Проверка пароля с хэшем
    public boolean verify(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}

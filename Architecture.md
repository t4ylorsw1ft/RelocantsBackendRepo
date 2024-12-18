
# Структура проекта Spring Boot Web API

## 1. **`src/main/java/com/example/relocationhelper/`**
Это основной каталог, в котором будут храниться все Java-классы вашего приложения.

### a. **`config/`** — Конфигурация Spring
В папке **`config/`** находятся все классы конфигурации для настройки поведения приложения. Примеры:
- **Spring Security** — настройка безопасности, например, аутентификация через JWT.
- **CORS** — настройка разрешений на кросс-доменные запросы, чтобы разрешить доступ к API из других доменов.
- **Общие настройки** — другие настройки приложения, такие как конфигурация базы данных, кеширование, сессии и т.д.

Пример класса конфигурации безопасности:
```java
package com.example.relocationhelper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Отключение CSRF защиты (для API без состояния)
            .authorizeRequests()
            .antMatchers("/api/auth/**").permitAll()  // Разрешение анонимного доступа к аутентификации
            .anyRequest().authenticated();  // Все другие запросы требуют аутентификации
        return http.build();
    }
}
```

### b. **`controller/`** — REST-контроллеры
В папке **`controller/`** находятся все REST-контроллеры, которые обрабатывают HTTP-запросы и отвечают на них. Это ключевая часть для вашего API.

Контроллеры используют аннотации Spring (`@RestController`, `@GetMapping`, `@PostMapping`, и т.д.) для обработки запросов и отправки ответов.

Пример:
```java
package com.example.relocationhelper.controller;

import com.example.relocationhelper.dto.UserDTO;
import com.example.relocationhelper.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable int id) {
        return userService.getUserById(id);  // Запрос к сервису для получения данных пользователя
    }
}
```
Контроллеры обеспечивают маршрутизацию, а также отвечают на запросы с данными в формате JSON.

### c. **`dto/`** — DTO-объекты (Data Transfer Objects)
DTO объекты используются для передачи данных между слоями приложения, в том числе между контроллером и клиентом (например, в виде JSON). DTO помогают избежать передачи лишней информации и скрывают детали реализации сущностей.

Пример DTO:
```java
package com.example.relocationhelper.dto;

public class UserDTO {
    private int id;
    private String fullName;
    private String email;

    // Геттеры и сеттеры
}
```

### d. **`entity/`** — JPA-сущности
В этой папке находятся JPA-сущности, которые отображаются на таблицы в базе данных. Сущности помечаются аннотациями `@Entity`, `@Table`, `@Id` и другими для определения связи с таблицами.

Пример сущности:
```java
package com.example.relocationhelper.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fullName;
    private String email;

    // Геттеры и сеттеры
}
```

### e. **`exception/`** — Обработка ошибок
Эта папка содержит классы для обработки ошибок и исключений в приложении. Например, вы можете настроить глобальный обработчик ошибок с помощью аннотации `@ControllerAdvice`, чтобы обрабатывать исключения по всему приложению.

Пример обработки исключений:
```java
package com.example.relocationhelper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
```

### f. **`repository/`** — Репозитории
Репозитории — это интерфейсы для работы с базой данных через Spring Data JPA. Они автоматически предоставляют базовые CRUD-методы для сущностей.

Пример репозитория:
```java
package com.example.relocationhelper.repository;

import com.example.relocationhelper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, int> {
    // Дополнительные методы для работы с базой данных
}
```

### g. **`service/`** — Логика приложения
Сервисы содержат бизнес-логику вашего приложения. В них происходит обработка данных, полученных от репозиториев, а также выполнение вычислений и другой логики.

Пример сервиса:
```java
package com.example.relocationhelper.service;

import com.example.relocationhelper.dto.UserDTO;
import com.example.relocationhelper.entity.User;
import com.example.relocationhelper.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getId(), user.getFullName(), user.getEmail());
    }
}
```

### h. **`util/`** — Утилиты
В этой папке можно разместить вспомогательные классы, которые могут использоваться в различных частях приложения. Например, классы для работы с датами, строками или другие утилитарные функции.

Пример утилиты:
```java
package com.example.relocationhelper.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
}
```

### i. **`RelocationHelperApplication.java`** — Точка входа
Это главный класс приложения, который запускает Spring Boot приложение. Он помечен аннотацией `@SpringBootApplication`, что указывает Spring, что это точка входа.

Пример:
```java
package com.example.relocationhelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RelocationHelperApplication {
    public static void main(String[] args) {
        SpringApplication.run(RelocationHelperApplication.class, args);
    }
}
```

---

## 2. **`src/main/resources/`**
Это папка с ресурсами, используемыми приложением. Для Web API она будет содержать следующие элементы:

- **`application.yml`** — Конфигурационные параметры приложения (например, настройки безопасности, параметры подключения к базе данных, порты и другие параметры).
- **`static/`** — В этой папке обычно хранятся статические файлы, такие как изображения, CSS и JavaScript. В Web API они обычно не используются, но могут понадобиться, если вы хотите, чтобы API возвращало статические файлы.
- **`templates/`** — Шаблоны (не нужны в вашем случае, так как вы не используете MVC и не рендерите страницы).
- **`schema.sql`** — Скрипты для создания схемы базы данных. Может быть полезно, если вы не используете миграции Flyway или Liquibase.

Пример конфигурации в **`application.yml`**:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/relocationhelper
    username: root
    password: root
  server:
    port: 8080
```

---

Эта структура и пояснения обеспечат вам основу для работы над проектом Web API с использованием Spring Boot.

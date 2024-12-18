package org.example.relocantsbackend.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.example.relocantsbackend.dto.auth.LoginDTO;
import org.example.relocantsbackend.dto.auth.RegisterUserDTO;
import org.example.relocantsbackend.dto.events.EventDTO;
import org.example.relocantsbackend.entity.Event;
import org.example.relocantsbackend.entity.User;
import org.example.relocantsbackend.exception.NotFoundException;
import org.example.relocantsbackend.service.EventService;
import org.example.relocantsbackend.service.UserService;
import org.example.relocantsbackend.util.auth.JwtProvider;
import org.example.relocantsbackend.util.auth.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/future")
    public ResponseEntity<List<Event>> getFutureEvents() {
        LocalDateTime todayStart = LocalDateTime.now().toLocalDate().atStartOfDay();

        List<Event> futureEvents = eventService.getFutureEvents(todayStart);

        if (futureEvents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return ResponseEntity.ok(futureEvents);
    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@Valid @RequestBody EventDTO eventDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Integer)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Integer userId = (Integer) authentication.getPrincipal();

        Event event = new Event(
                userId,
                eventDTO.getTitle(),
                eventDTO.getDescription(),
                eventDTO.getStartDateTime(),
                eventDTO.getTotalSeats(),
                eventDTO.getRegisteredSeats()
        );

        // Сохраняем событие через сервис
        Event savedEvent = eventService.saveEvent(event);

        return ResponseEntity.ok(savedEvent);
    }
}

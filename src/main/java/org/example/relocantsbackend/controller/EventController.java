package org.example.relocantsbackend.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.example.relocantsbackend.dto.auth.LoginDTO;
import org.example.relocantsbackend.dto.auth.RegisterUserDTO;
import org.example.relocantsbackend.dto.events.EventDTO;
import org.example.relocantsbackend.dto.events.EventInviteDTO;
import org.example.relocantsbackend.entity.Event;
import org.example.relocantsbackend.entity.EventRegistration;
import org.example.relocantsbackend.entity.Notification;
import org.example.relocantsbackend.entity.User;
import org.example.relocantsbackend.exception.NotFoundException;
import org.example.relocantsbackend.service.EventRegistrationService;
import org.example.relocantsbackend.service.EventService;
import org.example.relocantsbackend.service.NotificationService;
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
    private final EventRegistrationService eventRegistrationService;
    private final NotificationService notificationService;

    @Autowired
    public EventController(EventService eventService,
                           EventRegistrationService eventRegistrationService,
                           NotificationService notificationService) {
        this.eventService = eventService;
        this.eventRegistrationService = eventRegistrationService;
        this.notificationService = notificationService;
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
                eventDTO.getRegisteredSeats(),
                eventDTO.getEventTypeId(),
                eventDTO.getPhotoUrl()
        );

        // Сохраняем событие через сервис
        Event savedEvent = eventService.saveEvent(event);

        return ResponseEntity.ok(savedEvent);
    }

    @PostMapping("/enroll/{eventId}")
    public ResponseEntity<String> enrollInEvent(@PathVariable int eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Integer)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        Integer userId = (Integer) authentication.getPrincipal();

        boolean alreadyRegistered = eventRegistrationService.isAlreadyRegistered(userId, eventId);
        if (alreadyRegistered) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already enrolled in this event");
        }

        EventRegistration registration = new EventRegistration();
        registration.setUserId(userId);
        registration.setEventId(eventId);
        registration.setIsActive(true);
        registration.setAddress(null);

        try {
            eventRegistrationService.saveRegistration(registration);
            return ResponseEntity.ok("User successfully enrolled in the event");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to enroll in the event");
        }
    }

    @DeleteMapping("/enroll/{eventId}")
    public ResponseEntity<String> unenrollFromEvent(@PathVariable int eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Integer)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        Integer userId = (Integer) authentication.getPrincipal();

        boolean alreadyRegistered = eventRegistrationService.isAlreadyRegistered(userId, eventId);
        if (!alreadyRegistered) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registration not found for this event");
        }

        try {
            eventRegistrationService.deleteRegistration(userId, eventId);
            return ResponseEntity.ok("User successfully unenrolled from the event");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to unenroll from the event");
        }
    }

    @PostMapping("/invite")
    public ResponseEntity<String> inviteUserToEvent(@RequestBody EventInviteDTO inviteRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Integer)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        Integer userId = (Integer) authentication.getPrincipal();
System.out.println(inviteRequest.getEventId());
        Notification notification = new Notification(
                5,
                userId,
                inviteRequest.getEventId(),
                LocalDateTime.now()
        );

        try {
            notificationService.saveNotification(notification);
            return ResponseEntity.ok("Invitation sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send invitation");
        }
    }



}

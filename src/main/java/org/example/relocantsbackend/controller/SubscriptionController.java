package org.example.relocantsbackend.controller;
import org.example.relocantsbackend.dto.subscriptions.SubscriptionResponseDTO;
import org.example.relocantsbackend.entity.Notification;
import org.example.relocantsbackend.entity.Subscription;
import org.example.relocantsbackend.service.NotificationService;
import org.example.relocantsbackend.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friends")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final NotificationService notificationService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService,
                                  NotificationService notificationService) {
        this.subscriptionService = subscriptionService;
        this.notificationService = notificationService;
    }

    @GetMapping("/subscriptions/me")
    public ResponseEntity<List<SubscriptionResponseDTO>> getUserSubscriptions()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Integer)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Integer userId = (Integer) authentication.getPrincipal();

        try {
            List<SubscriptionResponseDTO> subscriptions = subscriptionService.getUserSubscriptions(userId);
            return ResponseEntity.ok(subscriptions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/subscribers/me")
    public ResponseEntity<List<SubscriptionResponseDTO>> getUserFollowers()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Integer)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Integer userId = (Integer) authentication.getPrincipal();

        try {
            List<SubscriptionResponseDTO> followers = subscriptionService.getUserFollowers(userId);
            return ResponseEntity.ok(followers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/subscriptions/{userId}")
    public ResponseEntity<List<SubscriptionResponseDTO>> getUserSubscriptions(@PathVariable int userId) {
        try {
            List<SubscriptionResponseDTO> subscriptions = subscriptionService.getUserSubscriptions(userId);
            return ResponseEntity.ok(subscriptions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/subscribers/{userId}")
    public ResponseEntity<List<SubscriptionResponseDTO>> getUserFollowers(@PathVariable int userId) {
        try {
            List<SubscriptionResponseDTO> followers = subscriptionService.getUserFollowers(userId);
            return ResponseEntity.ok(followers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/counts/me")
    public ResponseEntity<Map<String, Integer>> getSubscriptionCounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Integer)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Integer userId = (Integer) authentication.getPrincipal();

        try {
            Map<String, Integer> counts = subscriptionService.getSubscriptionCounts(userId);
            return ResponseEntity.ok(counts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyMap());
        }
    }

    @GetMapping("/counts/{userId}")
    public ResponseEntity<Map<String, Integer>> getSubscriptionCounts(@PathVariable int userId) {
        try {
            Map<String, Integer> counts = subscriptionService.getSubscriptionCounts(userId);
            return ResponseEntity.ok(counts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyMap());
        }
    }

    @PostMapping("/subscribe/{targetUserId}")
    public ResponseEntity<String> subscribe(@PathVariable int targetUserId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Integer)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Integer userId = (Integer) authentication.getPrincipal();

        try {
            subscriptionService.subscribe(userId, targetUserId);

            Notification notification = new Notification(
                    1,
                    targetUserId,
                    userId,
                    LocalDateTime.now(),
                    0
            );

            notificationService.saveNotification(notification);

            return ResponseEntity.ok("Successfully subscribed to user " + targetUserId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to subscribe");
        }
    }

    @DeleteMapping("/unsubscribe/{targetUserId}")
    public ResponseEntity<String> unsubscribe(@PathVariable int targetUserId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Integer)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Integer userId = (Integer) authentication.getPrincipal();

        try {
            subscriptionService.unsubscribe(userId, targetUserId);
            return ResponseEntity.ok("Successfully unsubscribed from user " + targetUserId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to unsubscribe");
        }
    }
}


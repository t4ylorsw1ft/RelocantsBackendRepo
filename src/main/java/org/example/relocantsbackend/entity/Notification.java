package org.example.relocantsbackend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int notificationTypeId;

    @Column(nullable = false)
    private int userId;

    private int relatedUserId;

    @Column(nullable = false)
    private LocalDateTime notificationDate;

    private int eventId;

    public Notification() {}

    public Notification(int notificationTypeId, int userId, int relatedUserId, LocalDateTime notificationDate, int eventId) {
        this.notificationTypeId = notificationTypeId;
        this.userId = userId;
        this.relatedUserId = relatedUserId;
        this.notificationDate = notificationDate;
        this.eventId = eventId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNotificationTypeId() {
        return notificationTypeId;
    }

    public void setNotificationTypeId(int notificationTypeId) {
        this.notificationTypeId = notificationTypeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRelatedUserId() {
        return relatedUserId;
    }

    public void setRelatedUserId(int relatedUserId) {
        this.relatedUserId = relatedUserId;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}

package org.example.relocantsbackend.dto.notifications;

import java.time.LocalDateTime;

public class NotificationResponseDTO {
    private Integer relatedUserId;
    private Integer eventId;
    private LocalDateTime notificationDate;
    private String notificationText;

    public NotificationResponseDTO(Integer relatedUserId, Integer eventId, LocalDateTime notificationDate, String notificationText) {
        this.relatedUserId = relatedUserId;
        this.eventId = eventId;
        this.notificationDate = notificationDate;
        this.notificationText = notificationText;
    }

    public Integer getRelatedUserId() {
        return relatedUserId;
    }

    public void setRelatedUserId(Integer relatedUserId) {
        this.relatedUserId = relatedUserId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    // Getters and setters
}

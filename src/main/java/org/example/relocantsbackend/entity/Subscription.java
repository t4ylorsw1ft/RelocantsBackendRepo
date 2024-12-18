package org.example.relocantsbackend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int subscriberId;

    @Column(nullable = false)
    private int subscribedToId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Subscription() {}

    public Subscription(int subscriberId, int subscribedToId, LocalDateTime createdAt) {
        this.subscriberId = subscriberId;
        this.subscribedToId = subscribedToId;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }

    public int getSubscribedToId() {
        return subscribedToId;
    }

    public void setSubscribedToId(int subscribedToId) {
        this.subscribedToId = subscribedToId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


package org.example.relocantsbackend.repository;

import org.example.relocantsbackend.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType, Integer> {
}


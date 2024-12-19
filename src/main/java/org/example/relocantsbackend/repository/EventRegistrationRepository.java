package org.example.relocantsbackend.repository;

import org.example.relocantsbackend.entity.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Integer> {
    boolean existsByUserIdAndEventId(int userId, int eventId);

    void deleteByUserIdAndEventId(int userId, int eventId);
}

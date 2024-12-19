package org.example.relocantsbackend.repository;
import org.example.relocantsbackend.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
}

package org.example.relocantsbackend.repository;

import org.example.relocantsbackend.entity.Event;
import org.example.relocantsbackend.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByStartDateTimeAfter(LocalDateTime now, Sort sort);

}

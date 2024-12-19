package org.example.relocantsbackend.service;

import org.example.relocantsbackend.entity.Event;
import org.example.relocantsbackend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getFutureEvents(LocalDateTime now) {
        Sort sort = Sort.by(Sort.Order.asc("startDateTime"));
        return eventRepository.findByStartDateTimeAfter(now, sort);
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }
}
package org.example.relocantsbackend.service;
import org.example.relocantsbackend.entity.EventType;
import org.example.relocantsbackend.repository.EventTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTypeService {

    private final EventTypeRepository eventTypeRepository;

    @Autowired
    public EventTypeService(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }
}


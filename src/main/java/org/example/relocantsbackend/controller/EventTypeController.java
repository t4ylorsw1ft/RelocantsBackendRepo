package org.example.relocantsbackend.controller;
import org.example.relocantsbackend.entity.EventType;
import org.example.relocantsbackend.service.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/event-types")
public class EventTypeController {

    private final EventTypeService eventTypeService;

    @Autowired
    public EventTypeController(EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    @GetMapping("")
    public List<EventType> getAllEventTypes() {
        return eventTypeService.getAllEventTypes();
    }
}

package org.example.relocantsbackend.service;

import jakarta.transaction.Transactional;
import org.example.relocantsbackend.entity.EventRegistration;
import org.example.relocantsbackend.repository.EventRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventRegistrationService {

    @Autowired
    private EventRegistrationRepository eventRegistrationRepository;

    public EventRegistration saveRegistration(EventRegistration registration) {
        return eventRegistrationRepository.save(registration);
    }

    public boolean eventExists(int eventId) {
        return eventRegistrationRepository.existsById(eventId);
    }

    public boolean isAlreadyRegistered(int userId, int eventId) {
        return eventRegistrationRepository.existsByUserIdAndEventId(userId, eventId);
    }

    @Transactional
    public void deleteRegistration(int userId, int eventId) {
        System.out.println("Deleting registration for userId=" + userId + ", eventId=" + eventId);

        boolean exists = eventRegistrationRepository.existsByUserIdAndEventId(userId, eventId);
        if (!exists) {
            System.out.println("Registration not found for userId=" + userId + ", eventId=" + eventId);
            throw new IllegalStateException("Registration not found");
        }

        eventRegistrationRepository.deleteByUserIdAndEventId(userId, eventId);
        System.out.println("Deletion successful for userId=" + userId + ", eventId=" + eventId);
    }


}

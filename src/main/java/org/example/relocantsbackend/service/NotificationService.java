package org.example.relocantsbackend.service;

import org.example.relocantsbackend.dto.notifications.NotificationResponseDTO;
import org.example.relocantsbackend.entity.Event;
import org.example.relocantsbackend.entity.Notification;
import org.example.relocantsbackend.entity.NotificationType;
import org.example.relocantsbackend.entity.User;
import org.example.relocantsbackend.repository.EventRepository;
import org.example.relocantsbackend.repository.NotificationRepository;
import org.example.relocantsbackend.repository.NotificationTypeRepository;
import org.example.relocantsbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationTypeRepository notificationTypeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;

    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public List<NotificationResponseDTO> getNotificationsForUser(int userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        List<NotificationResponseDTO> response = new ArrayList<>();

        for (Notification notification : notifications) {
            Integer relatedUserId = null;
            Integer eventId = null;
            String insertValue = "";

            // Определяем, что возвращать
            if (List.of(1, 4, 5).contains(notification.getNotificationTypeId())) {
                relatedUserId = notification.getRelatedUserId();
                insertValue = userRepository.findById(relatedUserId)
                        .map(User::getFullName)
                        .orElse("Unknown user");
            } else if (List.of(2, 3).contains(notification.getNotificationTypeId())) {
                eventId = notification.getEventId();
                insertValue = eventRepository.findById(eventId)
                        .map(Event::getTitle)
                        .orElse("Unknown event");
            }

            // Получаем текст уведомления и заменяем {{INSERT}}
            String notificationText = notificationTypeRepository.findById(notification.getNotificationTypeId())
                    .map(NotificationType::getMessage)
                    .orElse("Unknown notification type")
                    .replace("{{INSERT}}", insertValue);

            response.add(new NotificationResponseDTO(
                    relatedUserId,
                    eventId,
                    notification.getNotificationDate(),
                    notificationText
            ));
        }

        return response;
    }


}


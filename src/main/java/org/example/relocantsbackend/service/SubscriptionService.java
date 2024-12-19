package org.example.relocantsbackend.service;

import org.example.relocantsbackend.dto.subscriptions.SubscriptionResponseDTO;
import org.example.relocantsbackend.entity.Subscription;
import org.example.relocantsbackend.entity.User;
import org.example.relocantsbackend.repository.SubscriptionRepository;
import org.example.relocantsbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public List<SubscriptionResponseDTO> getUserSubscriptions(int userId) {
        List<Integer> subscribedUserIds = subscriptionRepository.getSubscribedUserIds(userId);

        List<User> subscribedUsers = userRepository.findAllById(subscribedUserIds);

        List<SubscriptionResponseDTO> subscriptions = new ArrayList<>();
        for (User subscribedUser : subscribedUsers) {
            boolean isSubscribed = subscriptionRepository.existsBySubscriberIdAndSubscribedToId(userId, subscribedUser.getId());
            subscriptions.add(new SubscriptionResponseDTO(subscribedUser.getId(), subscribedUser.getFullName(), isSubscribed));
        }
        return subscriptions;
    }

    public List<SubscriptionResponseDTO> getUserFollowers(int userId) {
        List<Integer> followerUserIds = subscriptionRepository.getFollowerUserIds(userId);

        List<User> followers = userRepository.findAllById(followerUserIds);

        List<SubscriptionResponseDTO> followersList = new ArrayList<>();
        for (User follower : followers) {
            boolean isSubscribed = subscriptionRepository.existsBySubscriberIdAndSubscribedToId(follower.getId(), userId);
            followersList.add(new SubscriptionResponseDTO(follower.getId(), follower.getFullName(), isSubscribed));
        }
        return followersList;
    }

    public Map<String, Integer> getSubscriptionCounts(int userId) {
        int subscriptionsCount = subscriptionRepository.countSubscriptionsByUserId(userId);  // Количество подписок
        int followersCount = subscriptionRepository.countFollowersByUserId(userId);  // Количество подписчиков

        // Возвращаем карту с количеством подписок и подписчиков
        Map<String, Integer> counts = new HashMap<>();
        counts.put("subscriptionsCount", subscriptionsCount);
        counts.put("followersCount", followersCount);

        return counts;
    }

    public void subscribe(int userId, int targetUserId) {
        if (subscriptionRepository.existsBySubscriberIdAndSubscribedToId(userId, targetUserId)) {
            throw new IllegalStateException("Already subscribed");
        }

        Subscription subscription = new Subscription();
        subscription.setSubscriberId(userId);
        subscription.setSubscribedToId(targetUserId);
        subscription.setCreatedAt(LocalDateTime.now());
        subscriptionRepository.save(subscription);
    }

    public void unsubscribe(int userId, int targetUserId) {
        Subscription subscription = subscriptionRepository.findBySubscriberIdAndSubscribedToId(userId, targetUserId);
        if (subscription == null) {
            throw new IllegalStateException("Not subscribed");
        }

        subscriptionRepository.delete(subscription);
    }
}


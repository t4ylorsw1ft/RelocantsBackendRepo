package org.example.relocantsbackend.repository;

import org.example.relocantsbackend.dto.subscriptions.SubscriptionResponseDTO;
import org.example.relocantsbackend.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    @Query("SELECT s.subscribedToId FROM Subscription s WHERE s.subscriberId = :userId")
    List<Integer> getSubscribedUserIds(@Param("userId") int userId);

    @Query("SELECT s.subscriberId FROM Subscription s WHERE s.subscribedToId = :userId")
    List<Integer> getFollowerUserIds(@Param("userId") int userId);

    @Query("SELECT COUNT(s) > 0 FROM Subscription s WHERE s.subscriberId = :subscriberId AND s.subscribedToId = :subscribedToId")
    boolean existsBySubscriberIdAndSubscribedToId(@Param("subscriberId") int subscriberId, @Param("subscribedToId") int subscribedToId);

    @Query("SELECT COUNT(s) FROM Subscription s WHERE s.subscriberId = :userId")
    int countSubscriptionsByUserId(@Param("userId") int userId);

    @Query("SELECT COUNT(s) FROM Subscription s WHERE s.subscribedToId = :userId")
    int countFollowersByUserId(@Param("userId") int userId);

    Subscription findBySubscriberIdAndSubscribedToId(int subscriberId, int subscribedToId);

}


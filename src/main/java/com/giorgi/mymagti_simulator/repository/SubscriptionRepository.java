package com.giorgi.mymagti_simulator.repository;

import com.giorgi.mymagti_simulator.entity.Subscriber;
import com.giorgi.mymagti_simulator.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findBySubscriberIdAndActiveTrue(Long subscriberId);
    List<Subscription> findAllByActiveTrueAndExpiryDateBefore(LocalDateTime now);
}

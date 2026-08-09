package com.giorgi.mymagti_simulator.repository;

import com.giorgi.mymagti_simulator.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findByPhoneNumber(Long phoneNumber);
    Boolean existsByPhoneNumber(Long phoneNumber);
}

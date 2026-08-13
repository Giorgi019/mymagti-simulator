package com.giorgi.mymagti_simulator.service;

import com.giorgi.mymagti_simulator.dto.SubscriberCreateDto;
import com.giorgi.mymagti_simulator.dto.SubscriberResponseDto;
import com.giorgi.mymagti_simulator.entity.Subscriber;
import com.giorgi.mymagti_simulator.entity.SubscriberStatus;
import com.giorgi.mymagti_simulator.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    @Transactional
    public SubscriberResponseDto createSubscriber(SubscriberCreateDto dto) {
        if (subscriberRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new RuntimeException("აბონენტი ამ ნომრით უკვე არსებობს!");
        }

        Subscriber subscriber = Subscriber.builder()
                .fullName(dto.getFullName())
                .phoneNumber(dto.getPhoneNumber())
                .balance(dto.getBalance())
                .status(SubscriberStatus.ACTIVE)
                .build();

        Subscriber saved = subscriberRepository.save(subscriber);
        return mapToResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public SubscriberResponseDto getSubscriberById(Long id) {
        Subscriber subscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("აბონენტი ვერ მოიძებნა ID-ით: " + id));
        return mapToResponseDto(subscriber);
    }

    @Transactional
    public SubscriberResponseDto topUpBalance(Long id, BigDecimal amount) {
        Subscriber subscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("აბონენტი ვერ მოიძებნა ID-ით: " + id));

        subscriber.setBalance(subscriber.getBalance().add(amount));
        Subscriber updated = subscriberRepository.save(subscriber);
        return mapToResponseDto(updated);
    }

    private SubscriberResponseDto mapToResponseDto(Subscriber subscriber) {
        return SubscriberResponseDto.builder()
                .id(subscriber.getId())
                .fullName(subscriber.getFullName())
                .phoneNumber(subscriber.getPhoneNumber())
                .balance(subscriber.getBalance())
                .status(subscriber.getStatus())
                .build();
    }
}
package com.giorgi.mymagti_simulator.service;

import com.giorgi.mymagti_simulator.dto.BuyPackageRequestDto;
import com.giorgi.mymagti_simulator.entity.ServicePackage;
import com.giorgi.mymagti_simulator.entity.Subscriber;
import com.giorgi.mymagti_simulator.entity.Subscription;
import com.giorgi.mymagti_simulator.exception.InsufficientBalanceException;
import com.giorgi.mymagti_simulator.exception.PackageNotFoundException;
import com.giorgi.mymagti_simulator.exception.SubscriberNotFoundException;
import com.giorgi.mymagti_simulator.repository.ServicePackageRepository;
import com.giorgi.mymagti_simulator.repository.SubscriberRepository;
import com.giorgi.mymagti_simulator.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriberRepository subscriberRepository;
    private final ServicePackageRepository servicePackageRepository;

    @Transactional
    public Subscription buyPackage(BuyPackageRequestDto dto) {
        log.info("Attempting to buy package for subscriberId: {} and packageId: {}", dto.getSubscriberId(), dto.getPackageId());

        Subscriber subscriber = subscriberRepository.findById(dto.getSubscriberId())
                .orElseThrow(() -> {
                    log.error("Subscriber not found with ID: {}", dto.getSubscriberId());
                    return new SubscriberNotFoundException("აბონენტი ვერ მოიძებნა ID-ით: " + dto.getSubscriberId());
                });

        ServicePackage servicePackage = servicePackageRepository.findById(dto.getPackageId())
                .orElseThrow(() -> {
                    log.error("Service package not found with ID: {}", dto.getPackageId());
                    return new PackageNotFoundException("პაკეტი ვერ მოიძებნა ID-ით: " + dto.getPackageId());
                });

        log.debug("Found subscriber: {} with balance: {}", subscriber.getFullName(), subscriber.getBalance());
        log.debug("Found service package: {} with price: {}", servicePackage.getName(), servicePackage.getPrice());

        if (subscriber.getBalance().compareTo(servicePackage.getPrice()) < 0) {
            log.warn("Insufficient balance for subscriberId: {}. Required: {}, Available: {}",
                    subscriber.getId(), servicePackage.getPrice(), subscriber.getBalance());
            throw new InsufficientBalanceException("არასაკმარისი ბალანსი! პაკეტის ფასი: "
                    + servicePackage.getPrice() + " ₾, თქვენი ბალანსი: " + subscriber.getBalance() + " ₾");
        }

        subscriber.setBalance(subscriber.getBalance().subtract(servicePackage.getPrice()));
        subscriberRepository.save(subscriber);
        log.info("Balance updated for subscriberId: {}. New balance: {}", subscriber.getId(), subscriber.getBalance());

        LocalDateTime now = LocalDateTime.now();
        Subscription subscription = Subscription.builder()
                .subscriber(subscriber)
                .servicePackage(servicePackage)
                .purchaseDate(now)
                .expiryDate(now.plusDays(servicePackage.getValidityDays()))
                .active(true)
                .build();

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        log.info("Successfully created new subscription with ID: {} for subscriberId: {}", savedSubscription.getId(), subscriber.getId());

        return savedSubscription;
    }

    @Transactional
    public void deactivateExpiredSubscriptions() {
        log.info("Starting scheduled job to deactivate expired subscriptions.");
        List<Subscription> expiredSubscriptions = subscriptionRepository.findAllByActiveTrueAndExpiryDateBefore(LocalDateTime.now());

        if (expiredSubscriptions.isEmpty()) {
            log.info("No expired subscriptions found.");
            return;
        }

        log.info("Found {} expired subscriptions to deactivate.", expiredSubscriptions.size());
        for (Subscription subscription : expiredSubscriptions) {
            subscription.setActive(false);
            log.debug("Deactivating subscription with ID: {} for subscriberId: {}", subscription.getId(), subscription.getSubscriber().getId());
        }
        subscriptionRepository.saveAll(expiredSubscriptions);
        log.info("Successfully deactivated {} expired subscriptions.", expiredSubscriptions.size());
    }
}

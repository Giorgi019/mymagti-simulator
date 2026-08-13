package com.giorgi.mymagti_simulator.service;

import com.giorgi.mymagti_simulator.dto.BuyPackageRequestDto;
import com.giorgi.mymagti_simulator.entity.ServicePackage;
import com.giorgi.mymagti_simulator.entity.Subscriber;
import com.giorgi.mymagti_simulator.entity.Subscription;
import com.giorgi.mymagti_simulator.repository.ServicePackageRepository;
import com.giorgi.mymagti_simulator.repository.SubscriberRepository;
import com.giorgi.mymagti_simulator.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriberRepository subscriberRepository;
    private final ServicePackageRepository servicePackageRepository;

    @Transactional
    public Subscription buyPackage(BuyPackageRequestDto dto){
        Subscriber subscriber = subscriberRepository.findById(dto.getSubscriberId())
                .orElseThrow(() ->new RuntimeException("აბონენტი ვერ მოიძებნა ID-ით: " + dto.getPackageId()));

        ServicePackage servicePackage = servicePackageRepository.findById(dto.getPackageId())
                .orElseThrow(() -> new RuntimeException("პაკეტი ვერ მოიძებნა ID-ით: " + dto.getPackageId()));

        if (subscriber.getBalance().compareTo(servicePackage.getPrice()) < 0) {
            throw new RuntimeException("არასაკმარისი ბალანსი! პაკეტის ფასი: "
                    + servicePackage.getPrice() + " ₾, თქვენი ბალანსი: " + subscriber.getBalance() + " ₾");
        }
        subscriber.setBalance(subscriber.getBalance().subtract(servicePackage.getPrice()));
        subscriberRepository.save(subscriber);

        LocalDateTime now = LocalDateTime.now();
        Subscription subscription = Subscription.builder()
                .subscriber(subscriber)
                .servicePackage(servicePackage)
                .purchaseDate(now)
                .expiryDate(now.plusDays(servicePackage.getValidityDays()))
                .active(true)
                .build();

        // 6. ბაზაში შენახვა
        return subscriptionRepository.save(subscription);
    }
}

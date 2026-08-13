package com.giorgi.mymagti_simulator.scheduler;

import com.giorgi.mymagti_simulator.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionScheduler {

    private final SubscriptionService subscriptionService;

    @Scheduled(cron = "0 0 0 * * ?") // Run every day at midnight
    public void deactivateExpiredSubscriptions() {
        subscriptionService.deactivateExpiredSubscriptions();
    }
}

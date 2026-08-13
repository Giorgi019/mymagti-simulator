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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriberRepository subscriberRepository;

    @Mock
    private ServicePackageRepository servicePackageRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private Subscriber subscriber;
    private ServicePackage servicePackage;
    private BuyPackageRequestDto buyPackageRequestDto;

    @BeforeEach
    void setUp() {
        subscriber = new Subscriber();
        subscriber.setId(1L);
        subscriber.setBalance(new BigDecimal("20.00"));

        servicePackage = new ServicePackage();
        servicePackage.setId(1L);
        servicePackage.setPrice(new BigDecimal("10.00"));
        servicePackage.setValidityDays(30);

        buyPackageRequestDto = new BuyPackageRequestDto();
        buyPackageRequestDto.setSubscriberId(1L);
        buyPackageRequestDto.setPackageId(1L);
    }

    @Test
    void buyPackage_Success() {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.of(subscriber));
        when(servicePackageRepository.findById(1L)).thenReturn(Optional.of(servicePackage));
        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Subscription result = subscriptionService.buyPackage(buyPackageRequestDto);

        assertNotNull(result);
        assertEquals(new BigDecimal("10.00"), subscriber.getBalance());
        assertTrue(result.isActive());
        verify(subscriberRepository, times(1)).save(subscriber);
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    void buyPackage_SubscriberNotFound() {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SubscriberNotFoundException.class, () -> {
            subscriptionService.buyPackage(buyPackageRequestDto);
        });
    }

    @Test
    void buyPackage_PackageNotFound() {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.of(subscriber));
        when(servicePackageRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PackageNotFoundException.class, () -> {
            subscriptionService.buyPackage(buyPackageRequestDto);
        });
    }

    @Test
    void buyPackage_InsufficientBalance() {
        subscriber.setBalance(new BigDecimal("5.00"));
        when(subscriberRepository.findById(1L)).thenReturn(Optional.of(subscriber));
        when(servicePackageRepository.findById(1L)).thenReturn(Optional.of(servicePackage));

        assertThrows(InsufficientBalanceException.class, () -> {
            subscriptionService.buyPackage(buyPackageRequestDto);
        });
    }
}

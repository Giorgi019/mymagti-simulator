package com.giorgi.mymagti_simulator.controller;

import com.giorgi.mymagti_simulator.dto.BuyPackageRequestDto;
import com.giorgi.mymagti_simulator.entity.Subscription;
import com.giorgi.mymagti_simulator.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/buy")
    public ResponseEntity<Subscription> buyPackage(@Valid @RequestBody BuyPackageRequestDto dto) {
        return ResponseEntity.ok(subscriptionService.buyPackage(dto));
    }
}

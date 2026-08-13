package com.giorgi.mymagti_simulator.controller;

import com.giorgi.mymagti_simulator.dto.SubscriberCreateDto;
import com.giorgi.mymagti_simulator.dto.SubscriberResponseDto;
import com.giorgi.mymagti_simulator.service.SubscriberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/subscribers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @PostMapping
    public ResponseEntity<SubscriberResponseDto> createSubscriber(@Valid @RequestBody SubscriberCreateDto dto) {
        return ResponseEntity.ok(subscriberService.createSubscriber(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriberResponseDto> getSubscriberById(@PathVariable Long id) {
        return ResponseEntity.ok(subscriberService.getSubscriberById(id));
    }

    @PostMapping("/{id}/top-up")
    public ResponseEntity<SubscriberResponseDto> topUpBalance(@PathVariable Long id, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(subscriberService.topUpBalance(id, amount));
    }
}
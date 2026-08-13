package com.giorgi.mymagti_simulator.dto;

import com.giorgi.mymagti_simulator.entity.SubscriberStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SubscriberResponseDto {

    private Long id;
    private String phoneNumber;
    private String fullName;
    private BigDecimal balance;
    private SubscriberStatus status;

}

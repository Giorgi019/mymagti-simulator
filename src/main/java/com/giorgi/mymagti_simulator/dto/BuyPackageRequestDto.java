package com.giorgi.mymagti_simulator.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BuyPackageRequestDto {

    @NotNull(message = "აბონენტის ID სავალდებულოა")
    private Long subscriberId;

    @NotNull(message = "პაკეტის ID სავალდებულოა")
    private Long packageId;

}

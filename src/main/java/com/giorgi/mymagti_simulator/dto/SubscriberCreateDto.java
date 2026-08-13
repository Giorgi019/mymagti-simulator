package com.giorgi.mymagti_simulator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubscriberCreateDto {

    @NotBlank(message = "ტელეფონის ნომერი სავალდებულოა")
    @Pattern(regexp = "^5\\d{8}$",message = "ნომერი უნდა იწყებოდეს 5-ით და შედგებოდეს 9 ციფრისგან")
    private String phoneNumber;

    @NotBlank(message = "სახელი სავალდებულოა")
    private String fullName;

    @NotNull(message = "ბალანსი სავალდებულოა")
    @PositiveOrZero(message = "საწყისი ბალანსი უარყოფითი ვერ იქნება")
    private BigDecimal balance;

}

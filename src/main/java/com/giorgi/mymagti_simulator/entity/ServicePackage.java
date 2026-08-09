package com.giorgi.mymagti_simulator.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "service_packages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServicePackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "პაკეტის სახელი სავალდებულოა")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PackageType packageType;

    @NotNull
    @Positive(message = "ფასი დადებითი რიცხვი უნდა იყოს")
    private BigDecimal price;

    private Integer megabytes;

    @NotNull
    @Min(value = 1, message = "ვადა სულ მცირე 1 დღე უნდა იყოს")
    private Integer validityDays;

}

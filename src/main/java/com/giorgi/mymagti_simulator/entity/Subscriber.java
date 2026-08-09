package com.giorgi.mymagti_simulator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "subscribers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ტელეფონის ნომერი სავალდებულოა")
    @Pattern(
            regexp = "^5\\d{8}$",
            message = "ნომერი უნდა იწყებოდეს 5-ით და შედგებოდეს 9 ციფრისგან"
    )
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @NotBlank(message = "სახელი სავალდებულოა")
    private String fullName;

    @NotNull
    @PositiveOrZero(message = "ბალანსი უარყოფითი ვერ იქნება")
    private BigDecimal balance;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SubscriberStatus status;

}

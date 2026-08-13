package com.giorgi.mymagti_simulator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}

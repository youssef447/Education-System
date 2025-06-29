package com.example.education_system.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentResponseDto {

    private String status;
    private String message;
    private String sessionUrl;
}

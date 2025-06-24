package com.example.education_system.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDto {
    private String clientSecret;
    private String paymentIntentId;
    private String status;
    private String message;
}

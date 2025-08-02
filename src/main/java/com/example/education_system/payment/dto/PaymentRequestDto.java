package com.example.education_system.payment.dto;

import com.example.education_system.payment.entity.Currency;



public record PaymentRequestDto(Long orderId, Currency currency
) {
}


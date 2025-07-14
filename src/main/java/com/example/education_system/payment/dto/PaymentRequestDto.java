package com.example.education_system.payment.dto;

import com.example.education_system.payment.entity.Currency;

import java.math.BigDecimal;


public record PaymentRequestDto(String courseCode, Long amount, Currency currency, Long quantity
) {
}


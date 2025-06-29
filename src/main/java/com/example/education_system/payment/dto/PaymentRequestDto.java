package com.example.education_system.payment.dto;

import com.example.education_system.payment.entity.Currency;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class PaymentRequestDto {
    private final String productName;
    private final Long amount;
    private final Currency currency;
    private final Long quantity;
}




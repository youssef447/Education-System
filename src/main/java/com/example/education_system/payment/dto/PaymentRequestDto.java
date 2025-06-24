package com.example.education_system.payment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class PaymentRequestDto {
    private final Long userId;
    private final Long amount;
    private final Currency currency;
}


enum Currency {
    EGP, USD
}

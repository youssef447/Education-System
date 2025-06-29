package com.example.education_system.payment.service;

import com.example.education_system.payment.dto.PaymentRequestDto;
import com.example.education_system.payment.dto.PaymentResponseDto;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentResponseDto pay(PaymentRequestDto request) throws StripeException;
}

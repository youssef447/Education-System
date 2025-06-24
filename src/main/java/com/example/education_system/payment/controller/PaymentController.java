package com.example.education_system.payment.controller;


import com.example.education_system.payment.dto.PaymentRequestDto;
import com.example.education_system.payment.dto.PaymentResponseDto;
import com.example.education_system.payment.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/v1")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/checkout")
    public ResponseEntity<PaymentResponseDto> checkout(@RequestBody PaymentRequestDto paymentRequest) throws StripeException {
        PaymentResponseDto stripeResponse = paymentService.pay(paymentRequest);
        return ResponseEntity.ok()
                .body(stripeResponse);
    }
}
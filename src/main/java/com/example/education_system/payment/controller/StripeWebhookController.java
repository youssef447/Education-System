package com.example.education_system.payment.controller;

import com.example.education_system.payment.service.WebhookService;
import com.stripe.exception.SignatureVerificationException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class StripeWebhookController {
    private final WebhookService webhookService;

    @PostMapping("/checkout")
    public ResponseEntity<String> checkoutEvent(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) throws SignatureVerificationException {
        return webhookService.handleStripeEvent(payload, sigHeader);

    }
}


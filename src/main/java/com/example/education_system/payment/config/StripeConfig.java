package com.example.education_system.payment.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.stripe.Stripe;

@Configuration
public class StripeConfig {
    @Value("${stripe.secret.key}")
    private String secretKey;
    @PostConstruct
    public void setup() {
        Stripe.apiKey = secretKey;
    }

}

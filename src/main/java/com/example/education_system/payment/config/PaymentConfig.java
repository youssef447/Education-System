package com.example.education_system.payment.config;


import com.example.education_system.payment.service.PaymentService;
import com.example.education_system.payment.service.StripeSessionPaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {
    @Bean
    PaymentService paymentService(){
        return new StripeSessionPaymentService();
    }
}

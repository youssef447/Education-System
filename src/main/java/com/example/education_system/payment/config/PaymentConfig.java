package com.example.education_system.payment.config;

import com.example.education_system.payment.repository.PaymentRepository;
import com.example.education_system.payment.service.PaymentService;
import com.example.education_system.payment.service.StripePaymentService;
import com.example.education_system.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {
    @Bean
    PaymentService paymentService(UserRepository UserRepository, PaymentRepository paymentRepository ){
        return new StripePaymentService(UserRepository,paymentRepository);
    }
}

package com.example.education_system.payment.service;

import com.example.education_system.payment.dto.PaymentRequestDto;
import com.example.education_system.payment.dto.PaymentResponseDto;
import com.example.education_system.payment.entity.PaymentEntity;
import com.example.education_system.payment.repository.PaymentRepository;
import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.repository.UserRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StripePaymentService implements PaymentService {
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    private PaymentIntent createPaymentIntent(PaymentRequestDto request) throws StripeException {


        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(request.getAmount())
                .setCurrency(request.getCurrency().name())
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                )
                .build();
        return PaymentIntent.create(params);


    }

    public PaymentResponseDto pay(PaymentRequestDto request) throws StripeException {

        PaymentIntent intent = createPaymentIntent(request);
        PaymentEntity payment = new PaymentEntity();
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setPaymentIntentId(intent.getId());
        payment.setStatus(intent.getStatus());

        savePayment(payment,request.getUserId());
        PaymentResponseDto response = new PaymentResponseDto();
        response.setClientSecret(intent.getClientSecret());
        response.setPaymentIntentId(intent.getId());
        response.setStatus(intent.getStatus());
        response.setMessage("Payment intent created successfully");


        return response;
    }




    private void savePayment(PaymentEntity payment,Long userId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        payment.setUser(user);
        paymentRepository.save(payment);
    }



}

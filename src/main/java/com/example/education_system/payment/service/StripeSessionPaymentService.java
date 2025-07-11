

package com.example.education_system.payment.service;

import com.example.education_system.payment.dto.PaymentRequestDto;
import com.example.education_system.payment.dto.PaymentResponseDto;

import com.stripe.exception.StripeException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StripeSessionPaymentService implements PaymentService {


    public PaymentResponseDto pay(PaymentRequestDto paymentRequest) throws StripeException {

        // Create a PaymentIntent with the order amount and currency
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(paymentRequest.productName())
                        .build();

        // Create new line item with the above product data and associated price
        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(paymentRequest.currency().name())
                        .setUnitAmount(paymentRequest.amount().multiply(BigDecimal.valueOf(100)))
                        .setProductData(productData)
                        .build();

        // Create new line item with the above price data
        SessionCreateParams.LineItem lineItem =
                SessionCreateParams
                        .LineItem.builder()
                        .setQuantity(paymentRequest.quantity())
                        .setPriceData(priceData)
                        .build();

        // Create session with the line items
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:8080/course/payment/success?session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl("http://localhost:8080/course/payment/fail")
                        .addLineItem(lineItem)
                        .build();

        // Create session
        Session session = Session.create(params);
        return PaymentResponseDto
                .builder()
                .status("SUCCESS")
                .message("Payment session created")
                .sessionUrl(session.getUrl())
                .build();
    }


}



package com.example.education_system.payment.service;

import com.example.education_system.payment.dto.PaymentRequestDto;
import com.example.education_system.payment.dto.PaymentResponseDto;
import com.example.education_system.payment.repository.PaymentRepository;
import com.example.education_system.user.repository.UserRepository;
import com.stripe.exception.StripeException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
@RequiredArgsConstructor
public class StripeSessionPaymentService implements PaymentService {
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    public PaymentResponseDto pay(PaymentRequestDto paymentRequest) throws StripeException {

        // Create a PaymentIntent with the order amount and currency
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(paymentRequest.getProductName())
                        .build();

        // Create new line item with the above product data and associated price
        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(paymentRequest.getCurrency().name())
                        .setUnitAmount(paymentRequest.getAmount() * 100)
                        .setProductData(productData)
                        .build();

        // Create new line item with the above price data
        SessionCreateParams.LineItem lineItem =
                SessionCreateParams
                        .LineItem.builder()
                        .setQuantity(paymentRequest.getQuantity())
                        .setPriceData(priceData)
                        .build();

        // Create session with the line items
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:8080/product/payment/success?session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl("http://localhost:8080/product/payment/fail")
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

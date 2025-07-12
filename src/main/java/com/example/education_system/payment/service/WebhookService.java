package com.example.education_system.payment.service;

import com.example.education_system.payment.entity.Currency;
import com.example.education_system.payment.entity.PaymentEntity;
import com.example.education_system.payment.entity.PaymentStatus;
import com.example.education_system.payment.repository.PaymentRepository;
import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.service.UserService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class WebhookService {
    private final PaymentRepository paymentRepository;
    private final UserService UserService;
    @Value("${stripe.webhook.secret}")
    private String endpointSecret;


    @Transactional
    public ResponseEntity<String> handleStripeEvent(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) throws SignatureVerificationException {

        Event event;

        event = Webhook.constructEvent(
                payload, sigHeader, endpointSecret
        );


        if ("checkout.session.completed".equals(event.getType())) {
            StripeObject stripeObject = null;
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            } else {
                return ResponseEntity.badRequest().body("Unable to deserialize payment intent from event");

            }
            if (stripeObject instanceof Session && ((Session) stripeObject).getPaymentStatus().equals("paid")) {

                savePayment((Session)stripeObject);
                // TODO saveOrder and save coupon redemption if exists
            }

        }

        return ResponseEntity.ok("");
    }

    private void savePayment(Session session) {
        Long amountTotal = session.getAmountTotal(); // In cents
        String currency = session.getCurrency();
        //String customerEmail = session.getCustomerDetails().getEmail();
        UserEntity user = UserService.getCurrentUser();
        BigDecimal dividedAmount = BigDecimal.valueOf(amountTotal)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        //payment data
        PaymentEntity payment = new PaymentEntity();
        payment.setAmount(dividedAmount);
        payment.setCurrency(Currency.valueOf(currency.toUpperCase()));
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setUser(user);
        paymentRepository.save(payment);
    }

}

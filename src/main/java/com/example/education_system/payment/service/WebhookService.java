package com.example.education_system.payment.service;


import com.example.education_system.payment.broker.PaymentProducer;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@RequiredArgsConstructor
public class WebhookService {
    private final PaymentProducer paymentProducer;
    @Value("${stripe.webhook.secret}")
    private String endpointSecret;


    public ResponseEntity<String> handleStripeEvent(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) throws SignatureVerificationException {

        Event event;

        event = Webhook.constructEvent(
                payload, sigHeader, endpointSecret
        );


        if ("payment_intent.succeeded".equals(event.getType())) {
            PaymentIntent intent;
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            if (dataObjectDeserializer.getObject().isPresent()) {
                intent = (PaymentIntent) dataObjectDeserializer.getObject().get();
            } else {
                return ResponseEntity.badRequest().body("Unable to deserialize payment intent from event");

            }
            paymentProducer.postPay(intent);


        }

        return ResponseEntity.ok("");
    }


}



package com.example.education_system.payment.service;

import com.example.education_system.config.exceptions.classes.CouponNotFound;
import com.example.education_system.config.exceptions.classes.OrderNotFoundException;
import com.example.education_system.course_coupon.entity.CouponEntity;
import com.example.education_system.course_coupon.repository.CouponRepository;
import com.example.education_system.course_coupon.service.CouponService;
import com.example.education_system.order.OrderEntity;
import com.example.education_system.order.OrderRepository;
import com.example.education_system.payment.dto.PaymentRequestDto;
import com.example.education_system.payment.dto.PaymentResponseDto;

import com.stripe.exception.StripeException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class StripeSessionPaymentService implements PaymentService {
    private final OrderRepository orderRepository;
    private final CouponService couponService;
    private final CouponRepository couponRepository;


    public PaymentResponseDto pay(PaymentRequestDto paymentRequest) throws StripeException {
        OrderEntity order = orderRepository.findById(paymentRequest.orderId()).
                orElseThrow(OrderNotFoundException::new);

        BigDecimal totalPrice = order.getTotalPrice();

        // Apply Coupon
        CouponEntity coupon = couponRepository.findByCode(paymentRequest.coupon())
                .orElseThrow(CouponNotFound::new);
        if (coupon != null) {
            totalPrice = calculateDiscount(totalPrice, coupon.getDiscountPercentage());
        }

        // Prepare product data
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName("#" + paymentRequest.orderId())
                        .build();

        // Prepare price data with the above product data and associated price
        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(paymentRequest.currency().name()).
                        setUnitAmountDecimal(totalPrice)
                        .setProductData(productData)
                        .build();

        // Create new line item with the above price data
        SessionCreateParams.LineItem lineItem =
                SessionCreateParams
                        .LineItem.builder()
                        //.setQuantity(paymentRequest.quantity())
                        .setPriceData(priceData)
                        .build();

        // Create session with the line items
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:8080/course/payment/success?session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl("http://localhost:8080/course/payment/fail")
                        .setPaymentIntentData(
                                SessionCreateParams.PaymentIntentData.builder()
                                        .putMetadata("order_id", paymentRequest.orderId().toString())
                                        .putMetadata("price", String.valueOf(totalPrice))
                                        .build()
                        )
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

    private BigDecimal calculateDiscount(BigDecimal total, BigDecimal discountPercentage) {
        BigDecimal discount = discountPercentage
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return total.multiply(BigDecimal.ONE.subtract(discount)).setScale(0, RoundingMode.HALF_UP);
    }
}

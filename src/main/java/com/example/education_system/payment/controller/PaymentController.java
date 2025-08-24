package com.example.education_system.payment.controller;


import com.example.education_system.config.response.ApiResponseBody;
import com.example.education_system.payment.dto.PaymentRequestDto;
import com.example.education_system.payment.dto.PaymentResponseDto;
import com.example.education_system.payment.service.PaymentService;
import com.stripe.exception.StripeException;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionRetrieveParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/checkout")
    @ResponseBody
    public ApiResponseBody checkout(@RequestBody PaymentRequestDto paymentRequest) throws StripeException {
        PaymentResponseDto stripeResponse = paymentService.pay(paymentRequest);
        return new ApiResponseBody("session created successfully", stripeResponse, true);
    }

    @GetMapping("/success")
    public String success(@RequestParam("session_id") String sessionId, Model model) throws StripeException {

        // Step 1: Expand line_items
        SessionRetrieveParams params = SessionRetrieveParams.builder()
                .addExpand("line_items.data.price.product")
                .addExpand("line_items")
                .build();

        Session session = Session.retrieve(sessionId, params, null);

        // Step 2: Get the first line item
        com.stripe.model.LineItem item = session.getLineItems().getData().get(0);

        // Step 3: Get product name, quantity, and amount
        Long amount = session.getAmountTotal() / 100;


        String product = item.getPrice().getProductObject().getName();
        Long quantity = item.getQuantity();
        String currency = item.getCurrency().toUpperCase();


        model.addAttribute("productName", product);
        model.addAttribute("quantity", quantity);
        model.addAttribute("amount", amount);
        model.addAttribute("currency", currency);
        return "success";
    }

    @GetMapping("/fail")
    public String fail() {
        return "fail";
    }
}
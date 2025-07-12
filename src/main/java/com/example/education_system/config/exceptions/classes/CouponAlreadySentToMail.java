package com.example.education_system.config.exceptions.classes;

public class CouponAlreadySentToMail extends RuntimeException {
    public CouponAlreadySentToMail() {
        super("coupons not available for this email");
    }
}

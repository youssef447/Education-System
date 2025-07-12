package com.example.education_system.config.exceptions.classes;

public class CouponWelcomeNotActive extends RuntimeException {
    public CouponWelcomeNotActive() {
        super("welcome coupon is currently disabled, please try again later");
    }
}

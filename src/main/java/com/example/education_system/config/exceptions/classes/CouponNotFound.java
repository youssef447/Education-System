package com.example.education_system.config.exceptions.classes;

public class CouponNotFound extends RuntimeException {
    public CouponNotFound() {
        super("coupon not found");
    }
}

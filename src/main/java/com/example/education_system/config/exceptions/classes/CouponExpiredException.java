package com.example.education_system.config.exceptions.classes;

public class CouponExpiredException extends RuntimeException {
    public CouponExpiredException() {
        super("coupon has expired");
    }
}

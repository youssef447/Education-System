package com.example.education_system.config.exceptions.classes;

public class CouponAlreadyExistsException extends RuntimeException {
    public CouponAlreadyExistsException() {
        super("coupon code must be unique");
    }
}

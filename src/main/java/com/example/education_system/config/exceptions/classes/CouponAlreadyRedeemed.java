package com.example.education_system.config.exceptions.classes;

public class CouponAlreadyRedeemed extends RuntimeException {
    public CouponAlreadyRedeemed() {
        super("coupon already redeemed");
    }
}

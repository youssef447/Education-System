package com.example.education_system.course_coupon.controller;

import com.example.education_system.config.response.ApiResponseBody;
import com.example.education_system.course_coupon.DTOS.CouponRedeemRequestDTO;
import com.example.education_system.course_coupon.DTOS.CouponRequestDTO;
import com.example.education_system.course_coupon.DTOS.CouponUserRequestDTO;
import com.example.education_system.course_coupon.DTOS.CouponValidateResponseDTO;
import com.example.education_system.course_coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/getWelcomeCoupon")
    @PreAuthorize("hasRole('USER')")
    ApiResponseBody getWelcomeCoupon(@RequestBody CouponUserRequestDTO request) {
        couponService.getWelcomeCoupon(request);
        return new ApiResponseBody("coupon sent successfully", true);
    }

    @PostMapping("/get")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody getAll() {
        return new ApiResponseBody("coupons fetched successfully", couponService.getAll(), true);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody add(@RequestBody CouponRequestDTO request) {
        return new ApiResponseBody("coupon added successfully", couponService.add(request), true);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody delete(@PathVariable Long id) {
        couponService.delete(id);
        return new ApiResponseBody("coupon deleted successfully", true);

    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody update(@RequestBody CouponRequestDTO request, @PathVariable Long id) {
        couponService.update(request, id);
        return new ApiResponseBody("coupon updated successfully", true);
    }

    @PostMapping("/validateCoupon")
    @PreAuthorize("hasRole('USER')")
    ApiResponseBody validateCoupon(@RequestBody CouponRedeemRequestDTO request) {
        CouponValidateResponseDTO result = couponService.validateCoupon(request);
        return new ApiResponseBody("coupon updated successfully", result, true);
    }
}

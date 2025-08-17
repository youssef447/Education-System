package com.example.education_system.course_coupon.controller;

import com.example.education_system.config.response.ApiResponseBody;
import com.example.education_system.course_coupon.DTOS.CouponRedeemRequestDTO;
import com.example.education_system.course_coupon.DTOS.CouponRedeemResponseDTO;
import com.example.education_system.course_coupon.DTOS.CouponRequestDTO;
import com.example.education_system.course_coupon.DTOS.CouponRegisterRequestDTO;
import com.example.education_system.course_coupon.DTOS.CouponRedeemResponseDTO;
import com.example.education_system.course_coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody getAll() {
        return new ApiResponseBody("coupons fetched successfully", couponService.getAll(), true);
    }

    @GetMapping("/welcome")
    @PreAuthorize("hasRole('USER')")
    ApiResponseBody getWelcomeCoupon(@RequestBody CouponRegisterRequestDTO request) {
        couponService.getWelcomeCoupon(request);
        return new ApiResponseBody("coupon sent successfully", true);
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody add(@RequestBody CouponRequestDTO request) {
        return new ApiResponseBody("coupon added successfully", couponService.add(request), true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody delete(@PathVariable Long id) {
        couponService.delete(id);
        return new ApiResponseBody("coupon deleted successfully", true);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody update(@RequestBody CouponRequestDTO request, @PathVariable Long id) {
        couponService.update(request, id);
        return new ApiResponseBody("coupon updated successfully", true);
    }

    @PostMapping("/validate")
    @PreAuthorize("hasRole('USER')")
    ApiResponseBody validateCoupon(@RequestBody CouponRedeemRequestDTO request) {
        CouponRedeemResponseDTO result = couponService.validateCoupon(request);
        return new ApiResponseBody("coupon updated successfully", result, true);
    }
}

package com.example.education_system.course_coupon.DTOS;

import com.example.education_system.course_coupon.entity.CouponEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record CouponResponseDTO(Long id,
                                String code,
                                CouponEntity.CouponType type,
                                LocalDateTime expirationDate,
                                int courseId, BigDecimal discountPercentage) {
}

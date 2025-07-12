package com.example.education_system.course_coupon.DTOS;

import lombok.Builder;

import java.time.LocalDateTime;


public record CouponResponseDTO(Long id,
                                String code,
                                LocalDateTime expirationDate,
                                int courseId, int discountValue) {
}

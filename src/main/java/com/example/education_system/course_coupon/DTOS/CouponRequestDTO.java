package com.example.education_system.course_coupon.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CouponRequestDTO {
    @NotBlank(message = "code is required")
    String code;
    @NotNull(message = "expiration date is required")
    LocalDateTime expirationDate;
    @NotNull(message = "discount percentage is required")
    private Double discountPercentage;
    @NotBlank(message = "coupon type is required")
    String couponType;
    //optional
    boolean isActive;
    Long courseId;


}

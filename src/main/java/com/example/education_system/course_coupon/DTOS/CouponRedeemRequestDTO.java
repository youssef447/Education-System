package com.example.education_system.course_coupon.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRedeemRequestDTO {
    @NotBlank(message = "coupon code is required")
    String code;
    @NotNull(message = "course id is required")
    Long courseId;

}

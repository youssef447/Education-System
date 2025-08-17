package com.example.education_system.course_coupon.DTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CouponRegisterRequestDTO {
    @NotBlank(message = "email is required")
    String email;


}

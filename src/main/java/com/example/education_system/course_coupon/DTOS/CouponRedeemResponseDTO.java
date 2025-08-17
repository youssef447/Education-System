package com.example.education_system.course_coupon.DTOS;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CouponRedeemResponseDTO {
    private BigDecimal discountedPrice;
    private BigDecimal originalPrice;
    private BigDecimal discountPercentage;

}

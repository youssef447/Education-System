package com.example.education_system.course_coupon.mapper;


import com.example.education_system.course_coupon.DTOS.CouponResponseDTO;
import com.example.education_system.course_coupon.entity.CouponEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CouponMapper {
    CouponResponseDTO toResponseDto(CouponEntity entity);



    List<CouponResponseDTO> toResponseListDto(List<CouponEntity> entity);


}
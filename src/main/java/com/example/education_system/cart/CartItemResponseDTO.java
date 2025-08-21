package com.example.education_system.cart;


import com.example.education_system.course.dto.CourseResponseDto;
import com.example.education_system.course.entity.CourseEntity;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class CartItemResponseDTO implements Serializable {

    private CourseResponseDto course;
    private BigDecimal unitPrice;
    private int quantity;
    private String thumbnailUrl;

}
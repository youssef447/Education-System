package com.example.education_system.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {


    @Mapping(target = "productId",source = "course.id")
    @Mapping(target = "productName",source = "course.courseCode")
    OrderItemResponseDTO toDTO(OrderItemEntity entity);
}

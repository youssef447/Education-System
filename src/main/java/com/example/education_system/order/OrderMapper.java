package com.example.education_system.order;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {



    @Mapping( target = "userId",source = "user.id")
    @Mapping( target = "currency",source = "currency")
    OrderResponseDTO toResponseDto(OrderEntity entity);

    List<OrderResponseDTO> toListDTO(List<OrderEntity> entity);
}
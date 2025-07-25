package com.example.education_system.category;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toDTO(CategoryEntity entity);

    @Mapping(target="iconFile",ignore = true)
    CategoryEntity toEntity(CategoryRequestDto request);

    List<CategoryResponseDto> toListDto(List<CategoryEntity> entity);
}

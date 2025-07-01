package com.example.education_system.category;

import com.example.education_system.courses.dto.CourseResponseDto;
import com.example.education_system.courses.entity.CourseEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toDTO(CategoryEntity entity);
    CategoryEntity toEntity(CategoryRequestDto request);
    List<CategoryResponseDto> toListDto(List<CategoryEntity> entity);
}

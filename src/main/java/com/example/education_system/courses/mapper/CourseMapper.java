package com.example.education_system.courses.mapper;

import com.example.education_system.courses.dto.CourseRequestDto;
import com.example.education_system.courses.dto.CourseResponseDto;
import com.example.education_system.courses.entity.CourseEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    List<CourseResponseDto> toListDto(List<CourseEntity> entity);

    CourseResponseDto toDto(CourseEntity entity);

    CourseEntity toEntity(CourseRequestDto dto);
}

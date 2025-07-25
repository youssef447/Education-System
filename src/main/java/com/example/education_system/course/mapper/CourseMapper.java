package com.example.education_system.course.mapper;

import com.example.education_system.course.dto.CourseRequestDto;
import com.example.education_system.course.dto.CourseResponseDto;
import com.example.education_system.course.entity.CourseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {


    @Mapping(target = "thumbnailUrl", source = "thumbnailFile.url")
    CourseResponseDto toDto(CourseEntity entity);

    List<CourseResponseDto> toListDto(List<CourseEntity> entity);

    CourseEntity toEntity(CourseRequestDto dto);
}

package com.example.education_system.course_lesson;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    LessonEntity toEntity(LessonRequestDTO request);

    @Mapping(target = "createdBy", expression = "java(entity.getCreatedBy().getUsername())")
    LessonResponseDTO toResponseDto(LessonEntity entity);
}

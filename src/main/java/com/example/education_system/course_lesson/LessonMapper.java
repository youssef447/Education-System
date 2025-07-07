package com.example.education_system.course_lesson;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    LessonEntity toEntity(LessonRequestDTO request);

    LessonResponseDTO toResponseDto(LessonEntity entity);
}

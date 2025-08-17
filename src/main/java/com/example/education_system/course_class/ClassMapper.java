package com.example.education_system.course_class;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassMapper {

    ClassResponseDTO toDTO(ClassEntity entity);

    @Mapping(target = "lessons",ignore = true)
    ClassEntity toEntity(ClassRequestDTO dto);

    List<ClassResponseDTO> toListDTO(List<ClassEntity> entity);
}

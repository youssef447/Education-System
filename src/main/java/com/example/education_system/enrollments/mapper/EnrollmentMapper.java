package com.example.education_system.enrollments.mapper;

import com.example.education_system.enrollments.dto.EnrollmentResponseDto;
import com.example.education_system.enrollments.entity.EnrollmentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    List<EnrollmentResponseDto> toListDto(List<EnrollmentEntity> entity);

    EnrollmentResponseDto toDto(EnrollmentEntity entity);
}

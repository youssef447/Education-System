package com.example.education_system.enrollment.mapper;

import com.example.education_system.enrollment.dto.EnrollmentResponseDto;
import com.example.education_system.enrollment.entity.EnrollmentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    List<EnrollmentResponseDto> toListDto(List<EnrollmentEntity> entity);

    EnrollmentResponseDto toDTO(EnrollmentEntity entity);
}

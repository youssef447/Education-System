package com.example.education_system.enrollment.mapper;

import com.example.education_system.enrollment.dto.EnrollmentRequestDTO;
import com.example.education_system.enrollment.dto.EnrollmentResponseDTO;
import com.example.education_system.enrollment.entity.EnrollmentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    List<EnrollmentResponseDTO> toListDto(List<EnrollmentEntity> entity);

    //  EnrollmentResponseDTO toDTO(EnrollmentEntity entity);

    //  EnrollmentEntity toEntity(EnrollmentRequestDTO dto);
}

package com.example.education_system.enrollment.mapper;


import com.example.education_system.enrollment.dto.EnrollmentResponseDTO;
import com.example.education_system.enrollment.entity.EnrollmentEntity;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {
    public EnrollmentResponseDTO toDto(EnrollmentEntity entity) {
        return EnrollmentResponseDTO.builder()
                .id(entity.getId())
                .studentId(entity.getStudent().getId())
                .courseId( entity.getCourse().getId())
                .status( entity.getStatus().name())
                .enrolledAt(entity.getCreatedDate())
                .completedAt(entity.getCompletedAt())
                .progressPercent(entity.getProgressPercent())
                .build();
    }
}
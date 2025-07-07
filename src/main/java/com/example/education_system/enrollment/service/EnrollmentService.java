package com.example.education_system.enrollment.service;

import com.example.education_system.enrollment.dto.EnrollmentResponseDto;
import com.example.education_system.enrollment.entity.EnrollmentEntity;
import com.example.education_system.enrollment.mapper.EnrollmentMapper;
import com.example.education_system.enrollment.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;


   public List<EnrollmentResponseDto> getAllEnrollments() {
        List<EnrollmentEntity> enrollments = enrollmentRepository.findAll();
        return enrollmentMapper.toListDto(enrollments);
    }
}

package com.example.education_system.enrollment.service;

import com.example.education_system.config.exceptions.classes.CourseNotFoundException;
import com.example.education_system.course.entity.CourseEntity;
import com.example.education_system.course.repository.CourseRepository;
import com.example.education_system.enrollment.dto.EnrollmentRequestDTO;
import com.example.education_system.enrollment.dto.EnrollmentResponseDTO;
import com.example.education_system.enrollment.entity.EnrollmentEntity;
import com.example.education_system.enrollment.mapper.EnrollmentMapper;
import com.example.education_system.enrollment.repository.EnrollmentRepository;
import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository CourseRepository;
    private final EnrollmentMapper enrollmentMapper;


    public List<EnrollmentResponseDTO> getAllEnrollments() {
        List<EnrollmentEntity> enrollments = enrollmentRepository.findAll();
        return enrollmentMapper.toListDto(enrollments);
    }

    public void enroll(EnrollmentRequestDTO request) {
        CourseEntity course = CourseRepository.findById(request.getCourseId())
                .orElseThrow(CourseNotFoundException::new);
        UserEntity user = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not found"));

        EnrollmentEntity entity = new EnrollmentEntity();
        entity.setCourse(course);
        entity.setStudent(user);

        //entity.setPayment(request.g);
        enrollmentRepository.save(entity);
    }
}

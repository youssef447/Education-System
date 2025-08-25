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
import com.example.education_system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final UserService userService;
    private final EnrollmentMapper enrollmentMapper;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;


    public List<EnrollmentResponseDTO> getAll(Integer page, Integer size) {
        Pageable pageable = buildPageable(page, size);
        Page<EnrollmentEntity> paged = enrollmentRepository.findAll(pageable);
        return paged.stream()
                .map(enrollmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EnrollmentResponseDTO> getUserEnrollments(Integer page, Integer size) {
        Long currentUserId = userService.getCurrentUser().getId();
        Pageable pageable = buildPageable(page, size);
        Page<EnrollmentEntity> paged =
                enrollmentRepository.findByStudentId(currentUserId, pageable);
        return paged.stream()
                .map(enrollmentMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * User: enroll in a course
     */
    @Transactional
    public EnrollmentResponseDTO enrollUser(EnrollmentRequestDTO req) {
        UserEntity student = userRepository.findById(req.getStudentId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        CourseEntity course = courseRepository.findById(req.getCourseId())
                .orElseThrow(CourseNotFoundException::new);

        boolean exists = enrollmentRepository
                .existsByStudentIdAndCourseId(student.getId(), course.getId());
        if (exists) {
            throw new IllegalStateException("Already enrolled in this course");
        }

        EnrollmentEntity enrollment = EnrollmentEntity.builder()
                .student(student)
                .course(course)
                .status(EnrollmentEntity.EnrollmentStatus.ACTIVE)
                .build();

        enrollmentRepository.save(enrollment);
        return enrollmentMapper. toDto(enrollment);
    }




    @Transactional
    public void deleteEnrollment(Long enrollmentId) {
        if (!enrollmentRepository.existsById(enrollmentId)) {
            throw new IllegalArgumentException("Enrollment not found");
        }
        enrollmentRepository.deleteById(enrollmentId);
    }

    //--- helpers ----------------------------------------------------------------

    private Pageable buildPageable(Integer page, Integer size) {
        int p = (page != null && page > 0) ? page : 0;
        int s = (size != null && size > 0) ? size : 20;
        return PageRequest.of(p, s);
    }


}


package com.example.education_system.enrollment.repository;

import com.example.education_system.enrollment.entity.EnrollmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    Page<EnrollmentEntity> findByStudentId(Long studentId, Pageable pageable);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}

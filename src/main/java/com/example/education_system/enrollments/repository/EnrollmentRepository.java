package com.example.education_system.enrollments.repository;

import com.example.education_system.courses.entity.CourseEntity;
import com.example.education_system.enrollments.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity,Long> {
}

package com.example.education_system.course.repository;

import com.example.education_system.course.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    boolean existsByCourseCode(String courseCode);
}

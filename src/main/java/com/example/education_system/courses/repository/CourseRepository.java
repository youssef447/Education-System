package com.example.education_system.courses.repository;

import com.example.education_system.courses.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface  CourseRepository extends JpaRepository<CourseEntity,Long> {
}

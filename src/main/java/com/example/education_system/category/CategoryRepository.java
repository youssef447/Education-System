package com.example.education_system.category;

import com.example.education_system.course.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Boolean existsByName(String name);

}

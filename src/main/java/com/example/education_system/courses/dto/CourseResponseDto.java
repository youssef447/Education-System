package com.example.education_system.courses.dto;

import com.example.education_system.category.CategoryResponseDto;
import com.example.education_system.enrollments.dto.EnrollmentResponseDto;
import com.example.education_system.user.dto.UserResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CourseResponseDto {
    private Long id;
    private String title;
    private String courseCode;
    private String description;
    private String thumbnailUrl;
    private LocalDateTime createdAt;
    // Nested DTOs for relationships
    private List<UserResponseDto> instructors;
    private Set<EnrollmentResponseDto> enrollments;
    private List<CategoryResponseDto> categories;
}

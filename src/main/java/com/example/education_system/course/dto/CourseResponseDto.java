package com.example.education_system.course.dto;


import com.example.education_system.category.CategoryResponseDto;
import com.example.education_system.enrollment.dto.EnrollmentResponseDto;
import com.example.education_system.user.dto.UserResponseDto;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public record CourseResponseDto(
        Long id,
        String title,
        String courseCode,
        String description,
        String thumbnailUrl,
        LocalDateTime createdAt,
        // Nested DTOs for relationships
        List<UserResponseDto> instructors,
        Set<EnrollmentResponseDto> enrollments,
        List<CategoryResponseDto> categories) {
}

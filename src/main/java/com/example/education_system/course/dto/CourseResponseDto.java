package com.example.education_system.course.dto;


import com.example.education_system.category.CategoryResponseDto;
import com.example.education_system.user.dto.UserResponseDto;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


public record CourseResponseDto(
        Long id,
        String title,
        String courseCode,
        String description,
        String thumbnailUrl,
        LocalDateTime createdAt,
        // Nested DTOs for relationships
        List<UserResponseDto> instructors,
        List<CategoryResponseDto> categories) implements Serializable {
}

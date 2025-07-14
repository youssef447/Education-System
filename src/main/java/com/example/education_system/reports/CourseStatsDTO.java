package com.example.education_system.reports;


import com.example.education_system.course.dto.CourseResponseDto;


public record CourseStatsDTO(CourseResponseDto course, Long totalQuantity, Double totalRevenue) {
}


package com.example.education_system.reports;


import com.example.education_system.course.dto.CourseRequestDto;
import com.example.education_system.course.entity.CourseEntity;


public record CourseStatsDTO(CourseRequestDto course, Long totalQuantity, Double totalRevenue) {
}


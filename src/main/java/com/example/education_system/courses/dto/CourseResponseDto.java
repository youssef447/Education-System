package com.example.education_system.courses.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CourseResponseDto {
    private Long id;
    private String title;
    private String courseCode;
    private String description;
    private String thumbnailUrl;

    private List<SimpleUserDto> instructors;
    private List<SimpleCategoryDto> categories;
    private int enrollmentCount;
}

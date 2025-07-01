package com.example.education_system.courses.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseRequestDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Course code is required")
    private String courseCode;

    @NotBlank(message = "Description is required")
    private String description;

    private String thumbnailUrl;

    @NotEmpty(message = "At least one instructor is required")
    private List<Long> instructorIds;

    @NotEmpty(message = "At least one category is required")
    private List<Long> categoryIds;
}

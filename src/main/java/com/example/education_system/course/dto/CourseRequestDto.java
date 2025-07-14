package com.example.education_system.course.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
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
    private MultipartFile thumbnailFile;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0", inclusive = true, message = "price cannot be less that zero")
    private BigDecimal price;

    @NotEmpty(message = "At least one instructor is required")
    private List<Long> instructorIds;

    @NotEmpty(message = "At least one category is required")
    private List<Long> categoryIds;


}

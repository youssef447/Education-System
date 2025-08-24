package com.example.education_system.enrollment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentRequestDTO {
    @NotBlank(message = "course id is required")
    Long courseId;
    @NotBlank(message = "student id is required")
    Long studentId;

}

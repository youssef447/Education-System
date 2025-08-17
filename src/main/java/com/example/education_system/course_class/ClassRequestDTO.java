package com.example.education_system.course_class;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassRequestDTO {
    @NotBlank(message="class name is required")
    private  String name;
    private  String description;
    @NotNull(message = "class course is required")
    private Long courseId;
}

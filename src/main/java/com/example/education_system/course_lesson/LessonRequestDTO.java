package com.example.education_system.course_lesson;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonRequestDTO {

    @NotBlank(message = "lesson name is required")
    private String name;

    @NotNull(message = "lesson order number is required")
    private int orderNumber;
    @NotNull(message = "lesson class is required")
    private int classId;
}

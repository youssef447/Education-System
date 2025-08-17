package com.example.education_system.course_lesson;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class LessonRequestDTO {


    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Order number is required")
    @Min(value = 1, message = "Order number must be at least 1")
    private Integer orderNumber;
    @NotNull(message = "lesson class is required")
    private Long classId;
    @NotNull(message = "content must be provide")
    private MultipartFile contentFile;

}

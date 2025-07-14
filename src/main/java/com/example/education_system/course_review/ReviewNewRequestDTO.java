package com.example.education_system.course_review;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewNewRequestDTO {
    @NotNull(message = "rate is required")
    private Integer rate;
    @NotNull(message = "user id is required")
    private Long userId;
    @NotNull(message = "product id is required")
    private Long courseId;

    private String comment;
}

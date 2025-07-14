package com.example.education_system.course_review;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewUpdateRequestDTO {

    private int rate;

    private String comment;
}

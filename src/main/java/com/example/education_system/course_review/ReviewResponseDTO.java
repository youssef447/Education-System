package com.example.education_system.course_review;

import lombok.Builder;

@Builder
public record ReviewResponseDTO(String comment, int rate, String username, boolean approved) {

}

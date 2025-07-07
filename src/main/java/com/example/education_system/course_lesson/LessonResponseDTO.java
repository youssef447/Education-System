package com.example.education_system.course_lesson;

import lombok.Builder;


@Builder
public record LessonResponseDTO(Long id, String name, String createdBy, int orderNumber, Long classId) {
}

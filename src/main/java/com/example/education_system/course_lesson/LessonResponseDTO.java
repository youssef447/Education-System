package com.example.education_system.course_lesson;

import com.example.education_system.config.files.FileInfo;
import lombok.Builder;


@Builder
public record LessonResponseDTO(Long id, String name, FileInfo fileInfo, String createdBy, int orderNumber, Long classId) {
}

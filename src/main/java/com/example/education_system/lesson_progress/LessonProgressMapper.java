package com.example.education_system.lesson_progress;

import org.springframework.stereotype.Component;

@Component
public class LessonProgressMapper {
    public LessonProgressDTO toDTO(LessonProgressEntity ent) {
        return LessonProgressDTO.builder()
                .id(ent.getId())
                .enrollmentId(ent.getEnrollment().getId())
                .lessonId(ent.getLesson().getId())
                .status(ent.getStatus().name())
                .startedAt(ent.getStartedAt())
                .completedAt(ent.getCompletedAt())
                .build();
    }


}

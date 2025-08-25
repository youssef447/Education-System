package com.example.education_system.lesson_progress;



import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class LessonProgressDTO {
    private Long id;
    private Long enrollmentId;
    private Long lessonId;
    private String status;          // NOT_STARTED, IN_PROGRESS, COMPLETED
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
}


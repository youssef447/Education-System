package com.example.education_system.lesson_progress;


import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.course_lesson.LessonEntity;
import com.example.education_system.enrollment.entity.EnrollmentEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonProgressEntity extends AuditBaseEntity {


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private EnrollmentEntity enrollment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    private LessonEntity lesson;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.NOT_STARTED;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    public enum Status {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED
    }
}

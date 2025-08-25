package com.example.education_system.enrollment.entity;

import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.course.entity.CourseEntity;
import com.example.education_system.lesson_progress.LessonProgressEntity;
import com.example.education_system.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "enrollments")
public class EnrollmentEntity extends AuditBaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UserEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private CourseEntity course;


    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    // Denormalized percent complete (0–100)
    @Column(name = "progress_percent", nullable = false)
    private Integer progressPercent= 0;

    // One Enrollment ↔ Many LessonProgress
    @OneToMany(
            mappedBy = "enrollment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LessonProgressEntity> lessonProgressList = new ArrayList<>();;


    public enum EnrollmentStatus {

        ACTIVE,
        COMPLETED,
    }



}
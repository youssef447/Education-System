package com.example.education_system.enrollments.entity;

import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.courses.entity.CourseEntity;
import com.example.education_system.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentEntity extends AuditBaseEntity<Long> {
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "user must be provided")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "course must be provided")
    private CourseEntity course;

    @Column(nullable = false)
    private LocalDateTime enrollmentDate;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    public enum EnrollmentStatus {
        ACTIVE,
        COMPLETED,
        DROPPED
    }
}
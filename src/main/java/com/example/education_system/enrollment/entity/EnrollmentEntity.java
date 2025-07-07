package com.example.education_system.enrollment.entity;

import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.course.entity.CourseEntity;
import com.example.education_system.payment.entity.PaymentEntity;
import com.example.education_system.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "enrollments")
public class EnrollmentEntity extends AuditBaseEntity<Long> {
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "student must be provided")
    @JoinColumn(nullable = false)
    private UserEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "course must be provided")
    private CourseEntity course;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private PaymentEntity payment;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    public enum EnrollmentStatus {
        ACTIVE,
        COMPLETED,
    }
}
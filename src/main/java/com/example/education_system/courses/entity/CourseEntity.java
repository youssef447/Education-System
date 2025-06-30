package com.example.education_system.courses.entity;

import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.enrollments.entity.EnrollmentEntity;
import com.example.education_system.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEntity extends AuditBaseEntity<Long> {
    //Columns
    @Column(nullable = false)
    @NotNull(message = "title is required")
    private String title;
    @Column(nullable = false, unique = true)
    private String courseCode;
    @Column(length = 1000,nullable = false)
    @NotNull(message = "title is required")
    private String description;
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    //Relations
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    @JoinTable(
            name = "courses_instructors",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id")
    )
    private List<UserEntity> instructors;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<EnrollmentEntity> enrollments = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private List<CourseCategoryEntity> categories;
}

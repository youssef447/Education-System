package com.example.education_system.course.entity;

import com.example.education_system.category.CategoryEntity;
import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "courses")
public class CourseEntity extends AuditBaseEntity<Long> {
    //Columns
    @Column(nullable = false)
    //@NotNull(message = "title is required")
    private String title;
    @Column(nullable = false, unique = true)
    private String courseCode;
    @Column(length = 1000, nullable = false)
    //@NotNull(message = "description is required")
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    private String thumbnailUrl;


    //Relations
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "courses_instructors",
            joinColumns = @JoinColumn(name = "courses_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id")
    )
    private Set<UserEntity> instructors;


    @ManyToMany(fetch = FetchType.LAZY)
    private Set<CategoryEntity> categories;


}

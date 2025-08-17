package com.example.education_system.course_review;

import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.course.entity.CourseEntity;
import com.example.education_system.user.entity.UserEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewEntity extends AuditBaseEntity {


    @OneToOne
    @JoinColumn(nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference
    private CourseEntity course;
    @Min(value = 0, message = "rating cannot be less than 0")
    @Max(value = 5, message = "rating cannot be more than 5")
    private int rate = 0;

    private String comment;

    boolean approved = false;


}

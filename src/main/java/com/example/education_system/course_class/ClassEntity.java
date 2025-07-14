package com.example.education_system.course_class;

import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.course.entity.CourseEntity;
import com.example.education_system.course_lesson.LessonEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassEntity extends AuditBaseEntity<Long> {
    private String name;
    private String description;
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LessonEntity> lessons;
}

package com.example.education_system.category;


import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.config.services.FileInfo;
import com.example.education_system.course.entity.CourseEntity;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "categories")
public class CategoryEntity extends AuditBaseEntity<Long> {
    @NotNull(message = "category name is required")
    @Column(nullable = false, unique = true)

    private String name;
    private String description;
    private FileInfo iconFile;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<CourseEntity> courses;

}

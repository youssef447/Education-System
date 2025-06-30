package com.example.education_system.courses.entity;


import com.example.education_system.config.audit.AuditBaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CourseCategoryEntity extends AuditBaseEntity<Long> {


    private String name;

    private String description;

    private String iconUrl;


}

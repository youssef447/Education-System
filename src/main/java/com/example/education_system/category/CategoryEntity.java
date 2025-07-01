package com.example.education_system.category;


import com.example.education_system.config.audit.AuditBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CategoryEntity extends AuditBaseEntity<Long> {
    @NotNull(message = "category name is required")
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private String iconUrl;

}

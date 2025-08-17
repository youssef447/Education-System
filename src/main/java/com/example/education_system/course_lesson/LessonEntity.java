package com.example.education_system.course_lesson;


import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.config.services.FileInfo;
import com.example.education_system.course_class.ClassEntity;
import com.example.education_system.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lessons")
public class LessonEntity extends AuditBaseEntity {
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private int orderNumber;
    @Embedded
    private FileInfo fileInfo;
    /// Relationships
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private ClassEntity classEntity;
}

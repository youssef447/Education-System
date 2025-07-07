package com.example.education_system.course_lesson;


import com.example.education_system.config.audit.AuditBaseEntity;
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
public class LessonEntity extends AuditBaseEntity<Long> {
    @Column(nullable = false)
    private String name;
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;
    private int orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClassEntity classEntity;


}

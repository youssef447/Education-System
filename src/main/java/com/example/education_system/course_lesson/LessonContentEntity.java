package com.example.education_system.course_lesson;

import com.example.education_system.config.audit.AuditBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lesson_contents")
@Getter
@Setter
@NoArgsConstructor
/*@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "content_type", discriminatorType = DiscriminatorType.STRING)*/
public  class LessonContentEntity extends AuditBaseEntity<Long> {
    private String cloudinaryPublicId;
    private String url;
    @Enumerated(EnumType.STRING)
    private LessonContentType contentType;

    /// Relationships
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private LessonEntity lesson;


    public enum LessonContentType {
        FILE, VIDEO, IMAGE, RECORD
    }
}

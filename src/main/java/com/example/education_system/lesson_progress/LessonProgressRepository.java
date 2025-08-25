package com.example.education_system.lesson_progress;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgressEntity, Long> {

    /**
     * Fetch paged lesson-progress records for a given enrollment.
     */
    Page<LessonProgressEntity> findByEnrollmentId(Long enrollmentId, Pageable pageable);

    /**
     * Fetch a single lesson-progress record for a given enrollment + lesson.
     */
    Optional<LessonProgressEntity> findByEnrollmentIdAndLessonId(Long enrollmentId, Long lessonId);

    @Query("""
    SELECT COUNT(lp)
    FROM LessonProgressEntity lp
    WHERE lp.enrollment.id = :enrollmentId
    AND lp.status = 'COMPLETED'
    """)
    int countCompletedLessonsByEnrollmentId(@Param("enrollmentId") Long enrollmentId);

}

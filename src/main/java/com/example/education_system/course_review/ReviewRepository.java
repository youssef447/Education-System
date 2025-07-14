package com.example.education_system.course_review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query("SELECT AVG(r.rate) FROM ReviewEntity r WHERE r.course.id = :courseId")
    Optional<Double> findAverageRatingByCourseId(@Param("courseId") Long courseId);

    boolean existsByUserId(Long userId);

    List<ReviewEntity> findAllByCourseId(Long courseId);

    List<ReviewEntity> findByCourseIdAndApproved(Long id, boolean approved);

}

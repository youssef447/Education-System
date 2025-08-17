package com.example.education_system.course_lesson;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {

    Page<LessonEntity> findByClassEntityIdOrderByOrderNumber(Long classEntityId, Pageable pageable);

    List<LessonEntity> findByClassEntityIdOrderByOrderNumber(Long classEntityId);

}

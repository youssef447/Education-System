package com.example.education_system.course.repository;

import com.example.education_system.course.entity.CourseEntity;
import org.eclipse.angus.mail.imap.protocol.ID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    boolean existsByCourseCode(String courseCode);

    @Query("select c.price from CourseEntity c where c.id = ?1")
    Optional<BigDecimal> findPriceById(Long id);
}

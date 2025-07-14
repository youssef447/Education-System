package com.example.education_system.course_review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query("SELECT AVG(r.rate) FROM ReviewEntity r WHERE r.product.id = :productId")
    Optional<Double> findAverageRatingByProductId(@Param("productId") Long productId);

    List<ReviewEntity> findByProductId(Long productId);

    boolean existsByUserId(long userId);

    List<ReviewEntity> findAllByProductId(long productId);

    List<ReviewEntity> findByProductIdAndApproved(Long id, boolean approved);
}

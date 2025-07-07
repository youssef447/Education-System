package com.example.education_system.config.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StorageRepository extends JpaRepository<StorageEntity,Long> {
    @Query("SELECT s.publicId FROM StorageEntity s WHERE s.url = :url")
    String findPublicIdByUrl(@Param("url") String url);

    void deleteByPublicId(String publicId);
}

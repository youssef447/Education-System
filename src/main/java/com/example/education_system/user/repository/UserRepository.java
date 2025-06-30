package com.example.education_system.user.repository;

import com.example.education_system.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);
    Boolean existsByEmail(String email);
   Boolean existsByUsername(String username);
}

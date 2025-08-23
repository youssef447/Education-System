package com.example.education_system.course_class;


import java.time.LocalDateTime;

public record ClassResponseDTO(Long id, String name, LocalDateTime createdDate,
                               String description) {
}

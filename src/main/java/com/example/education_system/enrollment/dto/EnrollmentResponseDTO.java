package com.example.education_system.enrollment.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentResponseDTO {

    private Long id;
    private Long studentId;
    private Long courseId;
    private String status;
    private LocalDateTime enrolledAt;
    private LocalDateTime completedAt;
    private Integer progressPercent;
}

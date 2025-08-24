package com.example.education_system.enrollment.controller;

import com.example.education_system.config.response.ApiResponseBody;
import com.example.education_system.enrollment.dto.EnrollmentRequestDTO;
import com.example.education_system.enrollment.dto.EnrollmentResponseDTO;
import com.example.education_system.enrollment.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController("/enrollment")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody getAllEnrollments() {
        List<EnrollmentResponseDTO> result = enrollmentService.getAllEnrollments();
        return new ApiResponseBody("courses fetched successfully", result, true);

    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    ApiResponseBody enroll(EnrollmentRequestDTO request) {
     enrollmentService.enroll(request);
        return new ApiResponseBody("course enrolled successfully", true);

    }
}

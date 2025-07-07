package com.example.education_system.enrollment.controller;

import com.example.education_system.config.response.ApiResponseBody;
import com.example.education_system.enrollment.dto.EnrollmentResponseDto;
import com.example.education_system.enrollment.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController("/entrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping("/get")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody getAllEnrollments() {
        List<EnrollmentResponseDto> result = enrollmentService.getAllEnrollments();
        return new ApiResponseBody("courses fetched successfully", result, true);

    }
}

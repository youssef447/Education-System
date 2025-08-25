package com.example.education_system.enrollment.controller;

import com.example.education_system.config.response.ApiResponseBody;
import com.example.education_system.enrollment.dto.EnrollmentResponseDTO;
import com.example.education_system.enrollment.dto.EnrollmentRequestDTO;
import com.example.education_system.enrollment.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        var result = enrollmentService.getAll(page, size);
        return new ApiResponseBody(
                "Enrollments fetched successfully",
                result,
                true
        );
    }


    @GetMapping
    @PreAuthorize("hasRole('USER')")
    ApiResponseBody get(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        var result = enrollmentService.getUserEnrollments(page, size);
        return new ApiResponseBody(
                "Your enrollments fetched successfully",
                result,
                true
        );
    }


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    ApiResponseBody add(
            @Valid @RequestBody EnrollmentRequestDTO request
    ) {
        EnrollmentResponseDTO dto = enrollmentService.enrollUser(request);
        return new ApiResponseBody(
                "Enrollment created successfully",
                dto,
                true
        );
    }


    @DeleteMapping("/{enrollmentId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    ApiResponseBody delete(
            @PathVariable Long enrollmentId
    ) {
        enrollmentService.deleteEnrollment(enrollmentId);
        return new ApiResponseBody(
                "Enrollment deleted successfully",
                true
        );
    }
}

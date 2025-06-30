package com.example.education_system.courses.controller;

import com.example.education_system.config.response.ApiResponseBody;
import com.example.education_system.courses.dto.CourseResponseDto;
import com.example.education_system.courses.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody getAllCourses() {
        List<CourseResponseDto> result = courseService.getAllCourses();
        return new ApiResponseBody("courses fetched successfully", result, true);
    }
}

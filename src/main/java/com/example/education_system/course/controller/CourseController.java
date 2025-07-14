package com.example.education_system.course.controller;

import com.example.education_system.config.response.ApiResponseBody;
import com.example.education_system.course.dto.CourseRequestDto;
import com.example.education_system.course.dto.CourseResponseDto;
import com.example.education_system.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    //, consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody addCourse(@Valid @ModelAttribute CourseRequestDto request
    ) {
        CourseResponseDto result = courseService.addCourse(request);
        return new ApiResponseBody("course added successfully", result, true);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ApiResponseBody("course deleted successfully", true);
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody updateCourse(@PathVariable Long id, @Valid @ModelAttribute CourseRequestDto request
    ) {
        courseService.updateCourse(id, request);
        return new ApiResponseBody("course updated successfully", true);
    }
}

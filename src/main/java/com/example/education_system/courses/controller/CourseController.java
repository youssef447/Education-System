package com.example.education_system.courses.controller;

import com.example.education_system.config.response.ApiResponseBody;
import com.example.education_system.courses.dto.CourseRequestDto;
import com.example.education_system.courses.dto.CourseResponseDto;
import com.example.education_system.courses.service.CourseService;
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

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody addCourse(@Valid @ModelAttribute CourseRequestDto request, @RequestParam(value = "image", required = false) MultipartFile file) {
        CourseResponseDto result = courseService.addCourse(request, file);
        return new ApiResponseBody("course added successfully", result, true);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody deleteCourse(@RequestParam Long id) {
        courseService.deleteCourse(id);
        return new ApiResponseBody("course deleted successfully", true);

    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody updateCourse(@RequestParam Long id, @Valid @ModelAttribute CourseRequestDto request,
                                 @RequestParam(value = "image", required = false) MultipartFile file) {
        courseService.updateCourse(id, request, file);
        return new ApiResponseBody("course updated successfully", true);
    }
}

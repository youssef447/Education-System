package com.example.education_system.course.controller;

import com.example.education_system.config.response.ApiResponseBody;
import com.example.education_system.course.dto.CourseRequestDto;
import com.example.education_system.course.dto.CourseResponseDto;
import com.example.education_system.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody getAllCourses(@RequestParam(required = false) Integer page,
                                  @RequestParam(required = false) Integer size) {
        Page<CourseResponseDto> result = courseService.getAllCourses(page, size);
        return new ApiResponseBody("courses fetched successfully", result, true);
    }

    //, consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody addCourse( @ModelAttribute CourseRequestDto request
    ) {
        CourseResponseDto result = courseService.addCourse(request);
        return new ApiResponseBody("course added successfully", result, true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ApiResponseBody("course deleted successfully", true);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody updateCourse(@PathVariable Long id, @Valid @ModelAttribute CourseRequestDto request
    ) {
        courseService.updateCourse(id, request);
        return new ApiResponseBody("course updated successfully", true);
    }
}

package com.example.education_system.course_lesson;

import com.example.education_system.config.response.ApiResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @GetMapping("/getAll")
    ApiResponseBody getAll() {
        return new ApiResponseBody("lessons fetched successfully", true);
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('TEACHER')")
    ApiResponseBody add(@ModelAttribute LessonRequestDTO request
    ) {
        return new ApiResponseBody("lesson added successfully", true);
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    ApiResponseBody update(@ModelAttribute LessonRequestDTO request
    ) {
        return new ApiResponseBody("lesson updated successfully", true);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    ApiResponseBody delete(@PathVariable Long id) {
        return new ApiResponseBody("lesson deleted successfully", true);
    }
}

package com.example.education_system.course_lesson;

import com.example.education_system.config.response.ApiResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @GetMapping("/getAll")
    ApiResponseBody getAll() {
        return new ApiResponseBody("lessons fetched successfully", true);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    ApiResponseBody add() {
        return new ApiResponseBody("lesson added successfully", true);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    ApiResponseBody update() {
        return new ApiResponseBody("lesson updated successfully", true);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    ApiResponseBody delete(@PathVariable Long id) {
        return new ApiResponseBody("lesson deleted successfully", true);
    }
}

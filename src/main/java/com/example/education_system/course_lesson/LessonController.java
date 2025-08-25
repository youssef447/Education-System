package com.example.education_system.course_lesson;

import com.example.education_system.config.response.ApiResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/classes/{classId}/lessons")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponseBody add(@PathVariable Long classId,
                               @ModelAttribute LessonRequestDTO request) {
        LessonResponseDTO data = lessonService.add(classId, request);
        return new ApiResponseBody("Lesson added successfully", data, true);
    }

    @GetMapping("/classes/{classId}/lessons")
    public ApiResponseBody getAll(
            @PathVariable Long classId,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        List<LessonResponseDTO> data = lessonService.getAll(classId, page, size);
        return new ApiResponseBody("Lessons fetched successfully", data, true);
    }

    @PutMapping("/lessons/{lessonId}")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ApiResponseBody update(@PathVariable Long lessonId,
                                  @ModelAttribute LessonRequestDTO request) {
        LessonResponseDTO data = lessonService.update(lessonId, request);
        return new ApiResponseBody("Lesson updated successfully", data, true);
    }

    @DeleteMapping("/lessons/{lessonId}")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ApiResponseBody delete(@PathVariable Long lessonId) {
        lessonService.delete(lessonId);
        return new ApiResponseBody("Lesson deleted successfully", true);
    }
}

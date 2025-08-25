package com.example.education_system.lesson_progress;


import com.example.education_system.config.response.ApiResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/enrollment/{enrollmentId}/progress")
@RequiredArgsConstructor
public class LessonProgressController {

    private final LessonProgressService lessonProgressService;


    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody getAll(
            @PathVariable Long enrollmentId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        var result = lessonProgressService.getAll(enrollmentId, page, size);
        return new ApiResponseBody(
                "Lesson progress records fetched successfully",
                result,
                true
        );
    }


    @GetMapping
    @PreAuthorize("hasRole('USER')")
    ApiResponseBody get(
            @PathVariable Long enrollmentId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        var result = lessonProgressService.getByEnrollment(enrollmentId, page, size);
        return new ApiResponseBody(
                "Your lesson progress fetched successfully",
                result,
                true
        );
    }


    @PostMapping("/{lessonId}/start")
    @PreAuthorize("hasRole('USER')")
    ApiResponseBody start(
            @PathVariable Long enrollmentId,
            @PathVariable Long lessonId
    ) {
        LessonProgressDTO dto = lessonProgressService.markStarted(enrollmentId, lessonId);
        return new ApiResponseBody(
                "Lesson marked as started",
                dto,
                true
        );
    }

    /**
     * Mark lesson as completed
     */
    @PostMapping("/{lessonId}/complete")
    @PreAuthorize("hasRole('USER')")
    ApiResponseBody complete(
            @PathVariable Long enrollmentId,
            @PathVariable Long lessonId
    ) {
        LessonProgressDTO dto = lessonProgressService.markCompleted(enrollmentId, lessonId);
        return new ApiResponseBody(
                "Lesson marked as completed",
                dto,
                true
        );
    }

    /**
     * Fetch a single lesson progress record
     */
    @GetMapping("/{lessonId}")
    @PreAuthorize("hasRole('USER')")
    ApiResponseBody getOne(
            @PathVariable Long enrollmentId,
            @PathVariable Long lessonId
    ) {
        LessonProgressDTO dto = lessonProgressService.getProgress(enrollmentId, lessonId);
        return new ApiResponseBody(
                "Lesson progress fetched successfully",
                dto,
                true
        );
    }
}

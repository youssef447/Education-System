package com.example.education_system.course_class;

import com.example.education_system.config.response.ApiResponseBody;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;

    @GetMapping(value = ("/course/{courseId}/classes"))
    ApiResponseBody getAll(
            @PathVariable Long courseId
    ) {
        List<ClassResponseDTO> data = classService.getAll(courseId);
        return new ApiResponseBody("classes fetched successfully", data, true);
    }

    @PostMapping(value = "/course/{courseId}/classes")
    @PreAuthorize("hasRole('TEACHER')")
    ApiResponseBody add(@PathVariable Long courseId, @RequestBody ClassRequestDTO request
    ) {
        ClassResponseDTO data = classService.add(request);
        return new ApiResponseBody("class added successfully", data, true);
    }


    @PutMapping("/classes/{classId}")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ApiResponseBody update(@PathVariable Long classId,
                                  @ModelAttribute ClassRequestDTO request) {
        ClassResponseDTO data = classService.update(classId, request);
        return new ApiResponseBody("Lesson updated successfully", data, true);
    }

    @DeleteMapping(value = "/classes/{id}")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    ApiResponseBody delete(@PathVariable Long id
    ) {
        classService.delete(id);
        return new ApiResponseBody("class deleted successfully", true);
    }


}

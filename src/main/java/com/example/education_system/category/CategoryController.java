package com.example.education_system.category;

import com.example.education_system.config.response.ApiResponseBody;
import com.example.education_system.course.dto.CourseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody getAllCategories() {
        List<CategoryResponseDto> result = categoryService.getAllCategories();
        return new ApiResponseBody("categories fetched successfully", result, true);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody addCategory(@ModelAttribute CategoryRequestDto request) {
        CategoryResponseDto result = categoryService.addCategory(request);
        return new ApiResponseBody("category added successfully", result, true);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody deleteCategory(@RequestParam Long id) {
        categoryService.deleteCategory(id);
        return new ApiResponseBody("category deleted successfully", true);

    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody updateCategory(@PathVariable Long id,
                                   @ModelAttribute CategoryRequestDto request

    ) {
        categoryService.updateCategory(id, request);
        return new ApiResponseBody("category updated successfully", true);
    }

    @PostMapping("/courses/get")
    ApiResponseBody getCategoryCourses(@RequestParam Long id) {
        List<CourseResponseDto> courses = categoryService.getCategoryCourses(id);
        return new ApiResponseBody("category courses fetched successfully", courses, true);
    }
}

package com.example.education_system.category;

import com.example.education_system.config.response.ApiResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    ApiResponseBody addCategory(@Valid @RequestBody CategoryRequestDto request,@RequestParam(value = "image", required = false) MultipartFile file) {
        CategoryResponseDto result = categoryService.addCategory(request,file);
        return new ApiResponseBody("category added successfully", result, true);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody deleteCategory(@RequestParam Long id) {
        categoryService.deleteCategory(id);
        return new ApiResponseBody("category deleted successfully", true);

    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody updateCategory(@RequestParam Long id,
                                   @Valid @RequestBody CategoryRequestDto request,
                                   @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        categoryService.updateCategory(id, request, imageFile);
        return new ApiResponseBody("category updated successfully", true);
    }
}

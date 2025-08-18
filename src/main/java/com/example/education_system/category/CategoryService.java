package com.example.education_system.category;

import com.example.education_system.config.exceptions.classes.CategoryAlreadyExistsException;
import com.example.education_system.config.exceptions.classes.CategoryNotFound;

import com.example.education_system.config.services.FileInfo;
import com.example.education_system.config.services.FileStorageService;
import com.example.education_system.config.services.FileValidationService;
import com.example.education_system.course.dto.CourseResponseDto;
import com.example.education_system.course.entity.CourseEntity;
import com.example.education_system.course.mapper.CourseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CourseMapper courseMapper;
    private final FileStorageService fileStorageService;

    public List<CategoryResponseDto> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categoryMapper.toListDto(categories);
    }

    public CategoryResponseDto addCategory(@Valid CategoryRequestDto request) {
        isCategoryUnique(request.getName());
        CategoryEntity entity = categoryMapper.toEntity(request);
        MultipartFile imageFile = request.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            FileInfo iconFile = fileStorageService.store(imageFile, FileValidationService.IMAGE_TYPES);
            entity.setIconFile(iconFile);
        }
        CategoryEntity persisted = categoryRepository.save(entity);
        return categoryMapper.toDTO(persisted);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);

    }

    public void updateCategory(Long categoryId, @Valid CategoryRequestDto request) {
        CategoryEntity existing = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFound::new);

        isCategoryUnique(request.getName());
        // fields

        existing.setDescription(request.getDescription());
        existing.setName(request.getName());
        MultipartFile imageFile = request.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            FileInfo iconFile = fileStorageService.store(imageFile, FileValidationService.IMAGE_TYPES);
            existing.setIconFile(iconFile);
        }

        categoryRepository.save(existing);


    }

    List<CourseResponseDto> getCategoryCourses(Long id) {
        CategoryEntity existing = categoryRepository.findById(id).orElseThrow(CategoryNotFound::new);
        List<CourseEntity> courses = existing.getCourses();
        return courseMapper.toListDto(courses);
    }


    private void isCategoryUnique(String categoryName) {
        if (categoryRepository.existsByName(categoryName)) {
            throw new CategoryAlreadyExistsException();
        }
    }


}

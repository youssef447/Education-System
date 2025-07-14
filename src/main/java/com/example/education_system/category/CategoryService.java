package com.example.education_system.category;

import com.example.education_system.config.exceptions.classes.CategoryAlreadyExistsException;
import com.example.education_system.config.exceptions.classes.CategoryNotFound;
import com.example.education_system.config.exceptions.classes.CourseNotFoundException;

import com.example.education_system.config.services.FileStorageService;
import com.example.education_system.course.dto.CourseResponseDto;
import com.example.education_system.course.entity.CourseEntity;
import com.example.education_system.course.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Set;

@Service
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

    public CategoryResponseDto addCategory(CategoryRequestDto request, MultipartFile imageFile) {
        isCategoryUnique(request.getName());
        CategoryEntity entity = categoryMapper.toEntity(request);
        if (imageFile != null && !imageFile.isEmpty()) {
            String url = fileStorageService.store(imageFile);
            entity.setIconUrl(url);
        }
        CategoryEntity persisted = categoryRepository.save(entity);
        return categoryMapper.toDTO(persisted);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);

    }

    public void updateCategory(Long categoryId, CategoryRequestDto request, MultipartFile imageFile) {
        CategoryEntity existing = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFound::new);

        isCategoryUnique(request.getName());
        // fields

        existing.setDescription(request.getDescription());
        existing.setName(request.getName());
        if (imageFile != null && !imageFile.isEmpty()) {
            String url = fileStorageService.store(imageFile);
            existing.setIconUrl(url);
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

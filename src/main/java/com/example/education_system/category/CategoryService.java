package com.example.education_system.category;

import com.example.education_system.config.exceptions.classes.CategoryAlreadyExistsException;
import com.example.education_system.config.exceptions.classes.CourseNotFoundException;

import com.example.education_system.config.services.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final FileStorageService fileStorageService;

    public List<CategoryResponseDto> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categoryMapper.toListDto(categories);
    }

    public CategoryResponseDto addCategory(CategoryRequestDto request, MultipartFile imageFile) {
        validateCategoryName(request.getName());
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
                .orElseThrow(CourseNotFoundException::new);

        validateCategoryName(request.getName());
        // fields

        existing.setDescription(request.getDescription());
        existing.setName(request.getName());
        if (imageFile != null && !imageFile.isEmpty()) {
            String url = fileStorageService.store(imageFile);
            existing.setIconUrl(url);
        }

        categoryRepository.save(existing);


    }

    private void validateCategoryName(String categoryName) {
        if (categoryRepository.existsByName(categoryName)) {
            throw new CategoryAlreadyExistsException();
        }
    }
}

package com.example.education_system.course.service;

import com.example.education_system.category.CategoryEntity;
import com.example.education_system.category.CategoryRepository;
import com.example.education_system.config.exceptions.classes.CourseCodeAlreadyExistsException;
import com.example.education_system.config.exceptions.classes.CourseNotFoundException;
import com.example.education_system.config.files.FileInfo;
import com.example.education_system.config.files.FileStorageService;
import com.example.education_system.config.files.FileValidationService;
import com.example.education_system.course.dto.CourseRequestDto;
import com.example.education_system.course.dto.CourseResponseDto;
import com.example.education_system.course.entity.CourseEntity;
import com.example.education_system.course.mapper.CourseMapper;
import com.example.education_system.course.repository.CourseRepository;
import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;

    private final CourseMapper courseMapper;


    public Page<CourseResponseDto> getAllCourses(Integer page, Integer size) {
        if (page == null || size == null) {
            List<CourseEntity> courses = courseRepository.findAll();
            return new PageImpl<>(courseMapper.toListDto(courses));

        }
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseEntity> courses = courseRepository.findAll(pageable);
        return courses.map(courseMapper::toDto);

    }

    public CourseResponseDto addCourse(@Valid CourseRequestDto request) {

        validateCourseCode(request.getCourseCode());
        CourseEntity entity = courseMapper.toEntity(request);
        MultipartFile imageFile = request.getThumbnailFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            FileInfo thumbnailFile = fileStorageService.store(imageFile, FileValidationService.IMAGE_TYPES);
            entity.setThumbnailFile(thumbnailFile);
        }
        CourseEntity course = courseRepository.save(entity);
        return courseMapper.toDto(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);

    }

    @Transactional
    public void updateCourse(Long courseId, @Valid CourseRequestDto request) {
        CourseEntity existing = courseRepository.findById(courseId)
                .orElseThrow(CourseNotFoundException::new);

        validateCourseCode(request.getCourseCode());
        // fields
        existing.setTitle(request.getTitle());
        existing.setCourseCode(request.getCourseCode());
        existing.setDescription(request.getDescription());

        MultipartFile imageFile = request.getThumbnailFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            FileInfo thumbnailFile = fileStorageService.store(imageFile, FileValidationService.IMAGE_TYPES);
            existing.setThumbnailFile(thumbnailFile);
        }


        //Relations
        List<UserEntity> instructors = userRepository.findAllById(request.getInstructorIds());
        existing.setInstructors(new HashSet<>(instructors));

        List<CategoryEntity> categories = categoryRepository.findAllById(request.getCategoryIds());
        existing.setCategories(new HashSet<>(categories));

        courseRepository.save(existing);


    }

    private void validateCourseCode(String courseCode) {
        if (courseRepository.existsByCourseCode(courseCode)) {
            throw new CourseCodeAlreadyExistsException();
        }
    }
}

package com.example.education_system.course.service;

import com.example.education_system.category.CategoryEntity;
import com.example.education_system.category.CategoryRepository;
import com.example.education_system.config.exceptions.classes.CourseCodeAlreadyExistsException;
import com.example.education_system.config.exceptions.classes.CourseNotFoundException;
import com.example.education_system.config.services.FileStorageService;
import com.example.education_system.course.dto.CourseRequestDto;
import com.example.education_system.course.dto.CourseResponseDto;
import com.example.education_system.course.entity.CourseEntity;
import com.example.education_system.course.mapper.CourseMapper;
import com.example.education_system.course.repository.CourseRepository;
import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;

    private final CourseMapper courseMapper;


    public List<CourseResponseDto> getAllCourses() {
        List<CourseEntity> courses = courseRepository.findAll();
        return courseMapper.toListDto(courses);
    }

    public CourseResponseDto addCourse(CourseRequestDto request, MultipartFile imageFile) {

        validateCourseCode(request.getCourseCode());
        CourseEntity entity = courseMapper.toEntity(request);

        if (imageFile != null && !imageFile.isEmpty()) {
            String url = fileStorageService.store(imageFile);
            entity.setThumbnailUrl(url);
        }
        CourseEntity course = courseRepository.save(entity);
        return courseMapper.toDto(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);

    }

    @Transactional
    public void updateCourse(Long courseId, CourseRequestDto request, MultipartFile imageFile) {
        CourseEntity existing = courseRepository.findById(courseId)
                .orElseThrow(CourseNotFoundException::new);

        validateCourseCode(request.getCourseCode());
        // fields
        existing.setTitle(request.getTitle());
        existing.setCourseCode(request.getCourseCode());
        existing.setDescription(request.getDescription());
        if (imageFile != null && !imageFile.isEmpty()) {
            String url = fileStorageService.store(imageFile);
            existing.setThumbnailUrl(url);
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

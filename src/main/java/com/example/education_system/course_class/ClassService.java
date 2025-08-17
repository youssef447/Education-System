package com.example.education_system.course_class;


import com.example.education_system.config.exceptions.classes.ClassNotFound;
import com.example.education_system.config.exceptions.classes.CourseNotFoundException;
import com.example.education_system.course.entity.CourseEntity;
import com.example.education_system.course.repository.CourseRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class ClassService {
    private final ClassRepository classRepository;
    private final CourseRepository courseRepository;

    private final ClassMapper mapper;

    public List<ClassResponseDTO> getAll(Long courseId) {

        List<ClassEntity> data = classRepository.findByCourseId((courseId));
        return mapper.toListDTO(data);
    }

    public ClassResponseDTO add(@Valid ClassRequestDTO request) {
        ClassEntity entity = mapper.toEntity(request);
        ClassEntity persisted = classRepository.save(entity);
        return mapper.toDTO(persisted);
    }

    public ClassResponseDTO update(Long classId, @Valid ClassRequestDTO request) {
        CourseEntity courseEntity = courseRepository.findById(request.getCourseId())
                .orElseThrow(CourseNotFoundException::new);
        ClassEntity existingEntity = classRepository.findById(classId)
                .orElseThrow(ClassNotFound::new);

        existingEntity.setName(request.getName());
        existingEntity.setCourse(courseEntity);
        Optional.ofNullable(request.getDescription())
                .ifPresent(existingEntity::setDescription);


        ClassEntity updatedEntity = classRepository.save(existingEntity);
        return mapper.toDTO(updatedEntity);
    }

    public List<ClassResponseDTO> delete(Long id) {
        List<ClassEntity> data = classRepository.findAll();
        return mapper.toListDTO(data);
    }
}

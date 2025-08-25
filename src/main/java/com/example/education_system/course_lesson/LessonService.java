package com.example.education_system.course_lesson;

import com.example.education_system.config.exceptions.classes.ClassNotFound;
import com.example.education_system.config.exceptions.classes.LessonNotFoundException;
import com.example.education_system.config.files.FileInfo;
import com.example.education_system.config.files.FileStorageService;
import com.example.education_system.config.files.FileValidationService;
import com.example.education_system.course_class.ClassEntity;
import com.example.education_system.course_class.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ClassRepository classRepository;
    private final LessonMapper mapper;

    private final FileStorageService fileStorageService;

    LessonResponseDTO add(Long classId, LessonRequestDTO request) {
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(ClassNotFound::new);
        LessonEntity lessonEntity = mapper.toEntity(request);
        lessonEntity.setClassEntity(classEntity);
        MultipartFile file = request.getContentFile();
        if (file != null && !file.isEmpty()) {
            // delete the old file from storage
            Optional.ofNullable(lessonEntity.getFileInfo())
                    .ifPresent(f -> fileStorageService.delete(f.publicId()));

            FileInfo fileInfo = fileStorageService.store(file, FileValidationService.LESSON_FILE_TYPES);
            lessonEntity.setFileInfo(fileInfo);
        }


        LessonEntity persisted = lessonRepository.save(lessonEntity);
        return mapper.toResponseDto(persisted);

    }

    List<LessonResponseDTO> getAll(Long classId, Integer page, Integer size) {
        if (page == null || size == null) {
            List<LessonEntity> lessons = lessonRepository.findByClassEntityIdOrderByOrderNumber(classId);
            return mapper.toListDTO(lessons);

        } else {
            Pageable pageable = PageRequest.of(page, size, Sort.by("orderNumber").descending());

            Page<LessonEntity> paged = lessonRepository.findByClassEntityIdOrderByOrderNumber(classId, pageable);

            return paged.stream()
                    .map(mapper::toResponseDto)
                    .collect(Collectors.toList());
        }

    }


    LessonResponseDTO update(Long id, LessonRequestDTO request) {
        ClassEntity classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(ClassNotFound::new);
        LessonEntity entity = lessonRepository.findById(id)
                .orElseThrow(LessonNotFoundException::new);

        // Update fields
        entity.setTitle(request.getTitle());
        Optional.ofNullable(request.getDescription())
                .ifPresent(entity::setDescription);
        entity.setOrderNumber(request.getOrderNumber());
        entity.setClassEntity(classEntity);

        // If a new file is provided, replace the old one
        MultipartFile file = request.getContentFile();
        if (file != null && !file.isEmpty()) {
            // delete the old file from storage
            Optional.ofNullable(entity.getFileInfo())
                    .ifPresent(f -> fileStorageService.delete(f.publicId()));

            FileInfo fileInfo = fileStorageService.store(file, FileValidationService.LESSON_FILE_TYPES);
            entity.setFileInfo(fileInfo);
        }

        lessonRepository.save(entity);
        return mapper.toResponseDto(entity);
    }


    void delete(Long id) {
        lessonRepository.deleteById(id);
    }
}

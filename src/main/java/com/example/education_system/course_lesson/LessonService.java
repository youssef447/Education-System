package com.example.education_system.course_lesson;

import com.example.education_system.config.services.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final FileStorageService fileStorageService;


    LessonResponseDTO add(LessonRequestDTO request) {

        LessonEntity entity = new LessonEntity();
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setOrderNumber(request.getOrderNumber());
        entity.setTitle(request.getTitle());
        MultipartFile file = request.getContentFile();
        if (file != null && !file.isEmpty()) {
            String url = fileStorageService.store(file);
            LessonContentEntity contentEntity = new LessonContentEntity();
            contentEntity.setUrl(url);
            contentEntity.setCloudinaryPublicId(publicId);
            entity.setContent(contentEntity);

        }
    }


    LessonResponseDTO update(LessonRequestDTO request) {
    }


    void delete(Long id) {
        lessonRepository.deleteById(id);
    }
}

package com.example.education_system.config.files;

import com.example.education_system.config.exceptions.classes.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
public class FileValidationService {

  public static final Set<String> IMAGE_TYPES = Set.of(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    );

    public static final Set<String> LESSON_FILE_TYPES = Set.of(
            // Images
            "image/*",
            // Videos
            "video/mp4", "video/x-msvideo", "video/quicktime",
            // Readable files
            "application/pdf", "text/plain",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    public void validateFileType(MultipartFile file, Set<String> allowedTypes) {
        String contentType = file.getContentType();
        if (contentType == null || !allowedTypes.contains(contentType)) {
            throw new FileUploadException("File type not allowed: " + contentType);
        }
    }
}

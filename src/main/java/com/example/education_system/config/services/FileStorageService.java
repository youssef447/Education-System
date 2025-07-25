package com.example.education_system.config.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.education_system.config.exceptions.classes.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final Cloudinary cloudinary;

    public FileInfo store(MultipartFile file) {
        validateFileType(file);


        try {
            final Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String secureUrl = uploadResult.get("secure_url").toString();
            String publicId = uploadResult.get("public_id").toString();
            String fileSize = formatSize(file.getSize());
            String format = uploadResult.get("format").toString();

            return new FileInfo(format, publicId, secureUrl, fileSize);
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload file");
        }
    }

    public void delete(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new FileUploadException("Failed to delete file");
        }
    }


    private void validateFileType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null ||
                (!contentType.startsWith("image/") &&
                        !contentType.startsWith("video/") &&
                        !contentType.equals("application/pdf"))) {
            throw new FileUploadException("Only images, videos, and PDFs are allowed");
        }
    }

    private String formatSize(long bytes) {
        long KB = 1024L;
        long MB = 1024L * 1024L;
        long GB = 1024L * 1024L * 1024L;

        if (bytes < 1024) return bytes + " B";
        long size;
        if (bytes < MB) {
            size = bytes / KB;
            return size + " KB";
        }
        if (bytes < GB) {
            size = bytes / MB;
            return size + " MB";
        }
        size = bytes / GB;
        return size + " GB";
    }
}

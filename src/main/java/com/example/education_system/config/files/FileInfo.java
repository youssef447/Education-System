package com.example.education_system.config.files;

import jakarta.persistence.Embeddable;

@Embeddable
public record FileInfo(
        String format,
        String publicId,
        String url,
        String size) {

}

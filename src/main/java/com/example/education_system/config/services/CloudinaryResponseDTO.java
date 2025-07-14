package com.example.education_system.config.services;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CloudinaryResponseDTO {
    String type;
    String publicId;
    String url;
    Long size;

}

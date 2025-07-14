package com.example.education_system.category;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CategoryRequestDto {
    @NotBlank(message = "category name is required")
    private String name;
    @Size(min = 6)
    private String description;

    private MultipartFile imageFile;

}

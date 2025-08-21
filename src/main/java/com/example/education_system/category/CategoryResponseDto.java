package com.example.education_system.category;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CategoryResponseDto implements Serializable {
    String name;
    private String description;
    private String iconUrl;
}

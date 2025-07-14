package com.example.education_system.config.exceptions.classes;

public class CategoryNotFound extends RuntimeException {
    public CategoryNotFound() {
        super("category not found");
    }
}

package com.example.education_system.config.exceptions.classes;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException() {
        super("Category Already exists");
    }
}

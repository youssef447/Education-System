package com.example.education_system.config.exceptions.classes;

public class ClassNotFound extends RuntimeException {
    public ClassNotFound() {
        super("Class not found");
    }
}

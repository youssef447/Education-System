package com.example.education_system.config.exceptions.classes;

public class CourseCodeAlreadyExistsException extends RuntimeException {
    public CourseCodeAlreadyExistsException() {
        super("Course Code Already Exists");
    }
}

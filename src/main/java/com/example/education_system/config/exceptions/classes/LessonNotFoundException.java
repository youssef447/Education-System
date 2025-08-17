package com.example.education_system.config.exceptions.classes;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException() {
        super("Lesson Not Found");
    }
}

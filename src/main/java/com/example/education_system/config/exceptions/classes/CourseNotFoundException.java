package com.example.education_system.config.exceptions.classes;

public class CourseNotFoundException extends  RuntimeException{
    public CourseNotFoundException() {
        super("Course not found");
    }
}

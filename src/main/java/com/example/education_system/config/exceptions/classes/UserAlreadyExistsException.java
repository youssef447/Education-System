package com.example.education_system.config.exceptions.classes;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("User Already Exists, Please Login");
    }
}

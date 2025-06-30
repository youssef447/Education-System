package com.example.education_system.config.exceptions.classes;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException() {
        super("Username Already taken, Please pick another one");
    }
}

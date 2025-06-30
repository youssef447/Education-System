package com.example.education_system.config.exceptions.classes;

public class RegisteredAlreadyException extends RuntimeException {

    public RegisteredAlreadyException() {
        super("User Already registered, Please login");
    }
}

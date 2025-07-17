package com.example.education_system.config.exceptions.classes;

public class EmailAlreadyException extends RuntimeException {

    public EmailAlreadyException() {
        super("Email Already registered, Please login");
    }
}

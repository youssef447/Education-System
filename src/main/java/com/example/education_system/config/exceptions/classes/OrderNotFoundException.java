package com.example.education_system.config.exceptions.classes;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super("order not found");
    }
}

package com.example.education_system.config.exceptions.classes;

public class NoReminderFoundException extends RuntimeException {
    public NoReminderFoundException() {
        super("No Reminder Found");
    }

}

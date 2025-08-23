package com.example.education_system.email;


import java.io.Serializable;

public record EmailMessageDTO(String to, String subject, String body) implements Serializable {
}

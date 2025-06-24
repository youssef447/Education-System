package com.example.education_system.user.dto;

import com.example.education_system.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    private String profileUrl;
    private UserRole role = UserRole.ROLE_STUDENT;

}

package com.example.education_system.user.dto;

import com.example.education_system.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserResponseDto {
    private String email;
    private  String username;
    private  String profileUrl;
    private Set<UserRole> roles;
}

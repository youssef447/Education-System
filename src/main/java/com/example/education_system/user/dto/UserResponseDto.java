package com.example.education_system.user.dto;

import com.example.education_system.user.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String username;
    private String token;
    private String profileUrl;
    private UserRole role;
}

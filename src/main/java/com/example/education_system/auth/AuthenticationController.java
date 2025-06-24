package com.example.education_system.auth;

import com.example.education_system.config.response.ApiResponseBody;
import com.example.education_system.user.dto.UserLoginDto;
import com.example.education_system.user.dto.UserRegistrationDto;
import com.example.education_system.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ApiResponseBody register(
            @Valid @ModelAttribute UserRegistrationDto request,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        UserResponseDto result = service.register(request, imageFile);

        return new ApiResponseBody("User registered successfully", result, true);

    }

    @PostMapping("/login")
    public ApiResponseBody login(@Valid @RequestBody UserLoginDto request) {
        UserResponseDto result = service.login(request);

        return new ApiResponseBody("Login successful", result, true);
    }

}

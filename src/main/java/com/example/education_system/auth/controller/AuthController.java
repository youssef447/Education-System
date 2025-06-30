
package com.example.education_system.auth.controller;


import com.example.education_system.auth.service.AuthenticationService;
import com.example.education_system.auth.dto.LoginResponseDto;
import com.example.education_system.config.response.ApiResponseBody;
import com.example.education_system.auth.dto.RegistrationDto;
import com.example.education_system.auth.dto.LoginDto;
import com.example.education_system.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authService;


    @PostMapping("/register")
    public ApiResponseBody register(
            @Valid @ModelAttribute RegistrationDto request,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        UserResponseDto result = authService.register(request, imageFile);

        return new ApiResponseBody("User registered successfully", result, true);

    }

    @PostMapping("/login")
    public ApiResponseBody generateToken(@RequestBody LoginDto authRequest) {
      LoginResponseDto result=  authService.login(authRequest);
        return new ApiResponseBody("User logged in successfully", result, true);



    }
}
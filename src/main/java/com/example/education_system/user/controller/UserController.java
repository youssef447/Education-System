package com.example.education_system.user.controller;


import com.example.education_system.auth.dto.UserRequestDTO;
import com.example.education_system.config.response.ApiResponseBody;
import com.example.education_system.config.security.annotation.IsUserOwner;
import com.example.education_system.user.dto.UserResponseDto;
import com.example.education_system.user.entity.UserRole;
import com.example.education_system.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/user")

public class UserController {

    UserService userservice;


    @PostMapping("/update")
    @IsUserOwner
    ApiResponseBody updateUser(@RequestParam Long id, @Valid @ModelAttribute UserRequestDTO request,
                               @RequestParam(value = "image", required = false) MultipartFile file) {
        UserResponseDto result = userservice.updateUser(id, request, file);
        return new ApiResponseBody("user updated successfully", result, true);
    }

    @DeleteMapping("/delete/{id}")
    @IsUserOwner
    ApiResponseBody deleteUser(@PathVariable Long id) {
        userservice.deleteUser(id);
        return new ApiResponseBody("user deleted successfully", true);
    }

    @PostMapping("/updateRoles")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody updateUserRoles(@RequestParam Long id, @RequestBody Set<UserRole> roles
    ) {
        userservice.updateUserRoles(id, roles);
        return new ApiResponseBody("user roles updated successfully", true);
    }


}

package com.example.education_system.user.service;


import com.example.education_system.auth.dto.RegisterRequestDTO;
import com.example.education_system.config.services.FileInfo;
import com.example.education_system.config.services.FileStorageService;
import com.example.education_system.config.services.FileValidationService;
import com.example.education_system.user.dto.UserResponseDto;
import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.entity.UserRole;
import com.example.education_system.user.mapper.UserMapper;
import com.example.education_system.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder;

    public UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {

            return null;
        }

        String username = auth.getName();

        return userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
    }

    @Transactional
    public UserResponseDto updateUser(Long id, RegisterRequestDTO request, MultipartFile imageFile) {
        UserEntity existing = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + request.getUsername() + "' not found"));

        // fields
        existing.setEmail(request.getEmail());
        existing.setPassword(passwordEncoder.encode(request.getPassword()));
        if (imageFile != null && !imageFile.isEmpty())
            fileStorageService.delete(existing.getImageFile().publicId());
        {
            FileInfo fileInfo = fileStorageService.store(imageFile, FileValidationService.IMAGE_TYPES);
            existing.setImageFile(fileInfo);
        }


        UserEntity updatedUser = userRepository.save(existing);
        return userMapper.toResponseDto(updatedUser);

    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);


    }

    public void updateUserRoles(Long id, Set<UserRole> roles) {
        UserEntity existing = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        existing.setRoles(roles);


        userRepository.save(existing);

    }


}
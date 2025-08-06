
package com.example.education_system.auth.service;

import com.example.education_system.auth.dto.LoginResponseDto;
import com.example.education_system.config.exceptions.classes.EmailAlreadyException;
import com.example.education_system.config.exceptions.classes.UsernameAlreadyExistsException;
import com.example.education_system.config.security.utils.JwtUtil;
import com.example.education_system.config.services.FileInfo;
import com.example.education_system.config.services.FileStorageService;
import com.example.education_system.auth.dto.UserRequestDTO;
import com.example.education_system.auth.dto.LoginDto;
import com.example.education_system.user.dto.UserResponseDto;
import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.mapper.UserMapper;
import com.example.education_system.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final FileStorageService fileStorageService;
    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserResponseDto register(UserRequestDTO request, MultipartFile imageFile) {
        // if email already exists
        if (repository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyException();
        }
        // if username already exists
        if (repository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        UserEntity userEntity = userMapper.toEntity(request);
        if (imageFile != null && !imageFile.isEmpty()) {
            FileInfo fileInfo = fileStorageService.store(imageFile);
            userEntity.setImageFile(fileInfo);
        }
        repository.save(userEntity);

        return userMapper.toResponseDto(userEntity);

    }

    @Transactional
    public LoginResponseDto login(LoginDto loginDto) {
        Authentication auth = new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
        authenticationManager.authenticate(auth);
        String token = JwtUtil.generateToken(loginDto.getUsernameOrEmail());
        return new LoginResponseDto(token, new Date());
    }

}


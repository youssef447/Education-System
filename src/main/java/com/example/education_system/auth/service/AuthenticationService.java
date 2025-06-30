
package com.example.education_system.auth.service;

import com.example.education_system.auth.dto.LoginResponseDto;
import com.example.education_system.config.exceptions.classes.RegisteredAlreadyException;
import com.example.education_system.config.exceptions.classes.UsernameAlreadyExistsException;
import com.example.education_system.config.services.FileStorageService;
import com.example.education_system.auth.dto.RegistrationDto;
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
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final FileStorageService fileStorageService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserResponseDto register(RegistrationDto request, MultipartFile imageFile) {
// if email already exists
        if (repository.existsByEmail(request.getEmail())) {
            throw new RegisteredAlreadyException();
        }
        // if username already exists
        if (repository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        UserEntity userEntity = userMapper.toEntity(request);

        repository.save(userEntity);
        if (imageFile != null && !imageFile.isEmpty()) {
            String url = fileStorageService.store(imageFile);
            userEntity.setProfileUrl(url);
        }
        return userMapper.toResponseDto(userEntity);

    }

    @Transactional
    public LoginResponseDto login(LoginDto loginDto) {
        Authentication auth = new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
        authenticationManager.authenticate(auth);
        String token = jwtService.generateToken(loginDto.getUsernameOrEmail());
        return new LoginResponseDto(token, new Date());
    }

}


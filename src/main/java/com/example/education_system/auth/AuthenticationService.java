package com.example.education_system.auth;

import com.example.education_system.config.exceptions.classes.UserAlreadyExistsException;
import com.example.education_system.config.services.FileStorageService;
import com.example.education_system.user.dto.UserLoginDto;
import com.example.education_system.user.dto.UserRegistrationDto;
import com.example.education_system.user.dto.UserResponseDto;
import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.mapper.UserMapper;
import com.example.education_system.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.authentication.AuthenticationManager;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final FileStorageService fileStorageService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserResponseDto register(UserRegistrationDto request, MultipartFile imageFile) {

        // if email already exists
        if (repository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException();
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
    public UserResponseDto login(UserLoginDto loginDto) {
        Authentication auth = new  UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        authManager.authenticate(auth);

        // Find user by username
        UserEntity userEntity = repository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Verify password
        if (!passwordEncoder.matches(loginDto.getPassword(), userEntity.getPassword())) {

            throw new BadCredentialsException("Invalid credentials");
        }

        return userMapper.toResponseDto(userEntity);
    }

}

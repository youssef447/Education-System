
package com.example.education_system.auth;

import com.example.education_system.config.exceptions.classes.UserAlreadyExistsException;
import com.example.education_system.config.services.FileStorageService;
import com.example.education_system.user.dto.RegistrationDto;
import com.example.education_system.user.dto.UserLoginDto;
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
    public LoginResponseDto login(UserLoginDto loginDto) {
        Authentication auth = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        authenticationManager.authenticate(auth);
        String token = jwtService.generateToken(loginDto.getUsername());
        return new LoginResponseDto(token, new Date());
    }

}


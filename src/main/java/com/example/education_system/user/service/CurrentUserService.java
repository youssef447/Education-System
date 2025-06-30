package com.example.education_system.user.service;


import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository UserEntityRepository;

    public UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {

            return null;
        }

        String username = auth.getName();

        return UserEntityRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
    }


}
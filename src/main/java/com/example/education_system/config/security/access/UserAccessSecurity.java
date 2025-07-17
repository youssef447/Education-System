
package com.example.education_system.config.security.access;

import com.example.education_system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("userAccessSecurity")
@RequiredArgsConstructor
public class UserAccessSecurity {
    private final UserRepository userRepository;

    public boolean isOwner(Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findById(id)
                .map(user -> user.getUsername().equals(currentUsername))
                .orElse(false);
    }
}

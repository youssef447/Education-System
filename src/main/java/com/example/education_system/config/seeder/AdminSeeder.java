package com.example.education_system.config.seeder;

import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.entity.UserRole;
import com.example.education_system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String email;
    private final String password;

    AdminSeeder(UserRepository userRepository,
                PasswordEncoder passwordEncoder,
                @Value("${admin.email}") String email, @Value("${admin.password}") String password) {
        this.userRepository = userRepository;
        this.email = email;
        this.password = password;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail(email)) {
            UserEntity user = new UserEntity();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles(Set.of(UserRole.ROLE_ADMIN));

            userRepository.save(user);

        }
    }
}
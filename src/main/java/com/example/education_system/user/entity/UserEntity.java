package com.example.education_system.user.entity;

import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.config.files.FileInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserEntity extends AuditBaseEntity implements UserDetails {


    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Embedded
    private FileInfo imageFile;

    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = Set.of(UserRole.ROLE_STUDENT);


    // Spring Security requirements
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());

    }
}



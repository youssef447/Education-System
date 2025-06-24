package com.example.education_system.user.entity;


import com.example.education_system.config.bases.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity extends BaseEntity<Long> implements UserDetails {


    @Column(unique = true, nullable = false)
    private String username;

    private String password;
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;


    // private LocalDateTime createdAt;
   /* @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }*/

    // Spring Security requirements
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());

    }
}



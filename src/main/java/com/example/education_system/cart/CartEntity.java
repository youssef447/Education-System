package com.example.education_system.cart;

import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
public class CartEntity extends AuditBaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private UserEntity user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> items = new ArrayList<>();
}

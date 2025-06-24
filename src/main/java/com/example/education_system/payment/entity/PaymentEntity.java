package com.example.education_system.payment.entity;


import com.example.education_system.payment.Currency;
import com.example.education_system.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String paymentIntentId;



    @Column(nullable = false, precision = 10, scale = 2)
    private double amount;

    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)

    private UserEntity user;



    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}


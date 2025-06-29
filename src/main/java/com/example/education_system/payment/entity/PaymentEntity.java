package com.example.education_system.payment.entity;


import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity extends AuditBaseEntity<Long> {

    @NotNull(message = "Amount is required")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;@NotNull(message = "Amount is required")
    @Column(nullable = false)
    @NotNull(message = "product name is required")
    private String productName;

    @NotNull(message = "currency required")
    private Currency currency;
    private PaymentStatus status=PaymentStatus.PENDING;

    @ManyToOne
    private UserEntity user;






}


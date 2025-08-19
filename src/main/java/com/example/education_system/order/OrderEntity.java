package com.example.education_system.order;

import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity extends AuditBaseEntity {
    @Column(nullable = false)
    private int itemCount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(nullable = false)
    private BigDecimal totalPrice;
    @Column(nullable = false)
    private CurrencyEnum currency;


    /// RELATIONS
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();


    @PrePersist
    @PreUpdate
    public void calculateTotalPrice() {
        itemCount = items.size();

        this.totalPrice = items.stream()
                .map(OrderItemEntity::getTotalPrice
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public enum OrderStatus {
        PENDING, PAID
    }

    public enum CurrencyEnum {
        USD, EGP
    }

}

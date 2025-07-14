package com.example.education_system.order;

import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.course_coupon.entity.CouponEntity;
import com.example.education_system.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity extends AuditBaseEntity<Long> {
    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(nullable = false)
    private BigDecimal total;


    /// RELATIONS
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private CouponEntity appliedCoupon;




    public enum OrderStatus {
        PENDING, PAID
    }

}

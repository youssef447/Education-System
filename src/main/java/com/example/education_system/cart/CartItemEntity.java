package com.example.education_system.cart;

import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.course.entity.CourseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@Builder
public class CartItemEntity extends AuditBaseEntity<Long> {
    @JoinColumn(nullable = false)

    private BigDecimal currentPrice;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private CourseEntity course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private CartEntity cart;

}

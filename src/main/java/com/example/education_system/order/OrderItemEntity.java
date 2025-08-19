package com.example.education_system.order;

import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.course.entity.CourseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
public class OrderItemEntity extends AuditBaseEntity {

    @Column(nullable = false)
    private int quantity = 1;

    @Column(nullable = false)
    private BigDecimal unitPrice;
    @Column(nullable = false)
    private BigDecimal totalPrice;

    @OneToOne
    @JoinColumn(nullable = false)
    CourseEntity course;
    @ManyToOne
    @JoinColumn(nullable = false)
    OrderEntity order;

    @PrePersist
    @PreUpdate
    public void calcPrice() {
        unitPrice = course.getPrice();
        totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }


}

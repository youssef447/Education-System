package com.example.education_system.order;

import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.course.entity.CourseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.Join;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
public class OrderItemEntity extends AuditBaseEntity<Long> {

    @Column(nullable = false)
    private BigDecimal priceAtPurchase;

    @OneToOne
    @JoinColumn(nullable=false)
    CourseEntity course;


    @ManyToOne
    @JoinColumn(nullable=false)
    OrderEntity order;


}

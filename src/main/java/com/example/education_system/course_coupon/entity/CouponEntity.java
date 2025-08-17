package com.example.education_system.course_coupon.entity;


import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.course.entity.CourseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "coupons")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponEntity extends AuditBaseEntity {

    @Column(nullable = false, unique = true)
    private String code;

    LocalDateTime expirationDate;

    @Column(nullable = false)
    private BigDecimal discountPercentage;

    private boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    //CAN B
    private CourseEntity course;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CouponRedemptionEntity> redemptions;

    public enum CouponType {
        GLOBAL,
        COURSE_SPECIFIC
    }
}

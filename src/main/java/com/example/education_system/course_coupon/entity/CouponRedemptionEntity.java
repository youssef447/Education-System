package com.example.education_system.course_coupon.entity;

import com.example.education_system.config.audit.AuditBaseEntity;
import com.example.education_system.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "coupon_redemptions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponRedemptionEntity extends AuditBaseEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private CouponEntity coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;




}

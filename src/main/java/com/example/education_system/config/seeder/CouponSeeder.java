package com.example.education_system.config.seeder;

import com.example.education_system.course_coupon.entity.CouponEntity;
import com.example.education_system.course_coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CouponSeeder implements CommandLineRunner {

    private final CouponRepository couponRepository;

    @Override
    public void run(String... args) {
        if (!couponRepository.existsByCode("WELCOME0000")) {
            CouponEntity welcomeCoupon = CouponEntity.builder()
                    .code("WELCOME0000")
                    .discountPercentage(5.0)
                    .active(true)
                    .build();
            couponRepository.save(welcomeCoupon);

        }
    }
}


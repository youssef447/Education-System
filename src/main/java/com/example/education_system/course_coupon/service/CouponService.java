package com.example.education_system.course_coupon.service;

import com.example.education_system.config.exceptions.classes.*;
import com.example.education_system.config.services.EmailService;
import com.example.education_system.course.entity.CourseEntity;
import com.example.education_system.course.repository.CourseRepository;
import com.example.education_system.course_coupon.DTOS.*;
import com.example.education_system.course_coupon.entity.CouponEntity;
import com.example.education_system.course_coupon.mapper.CouponMapper;
import com.example.education_system.course_coupon.repository.CouponRepository;
import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.repository.UserRepository;
import com.example.education_system.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class CouponService {

    private final CouponRepository couponRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserService userService;
    private final CouponMapper couponMapper;

    public List<CouponResponseDTO> getAll() {
        List<CouponEntity> coupons = couponRepository.findAll();
        return couponMapper.toResponseListDto(coupons);
    }

    public CouponResponseDTO add(@Valid CouponRequestDTO request) {
        String code = null;
        if (request.getCode() == null) {
            code = generateCode();
        }
        if (couponRepository.existsByCode(code)) {
            throw new CouponAlreadyExistsException();
        }
        CourseEntity courseEntity = courseRepository.findById(request.getCourseId()).
                orElseThrow(CourseNotFoundException::new);

        CouponEntity entity = CouponEntity.builder().code(code)
                .course(courseEntity).expirationDate(request.getExpirationDate()).
                active(request.isActive())
                .type(CouponEntity.CouponType.valueOf(request.getCouponType().toUpperCase()))
                .discountPercentage(request.getDiscountPercentage()).build();

        couponRepository.save(entity);
        return couponMapper.toResponseDto(entity);
    }


    public void getWelcomeCoupon(@Valid CouponRegisterRequestDTO request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        if (user.getCreatedDate().isBefore(LocalDateTime.now().minusDays(7))) {
            throw new CouponNotFound();
        }
        String message = "Congratulations! 🎉\n\n" +
                "You have received a discount coupon.\n\n" +
                "Use this code at checkout: " + "WELCOME0000" + "\n" +
                "Hurry! The coupon is valid until: " + "\n" + LocalDateTime.now().plusDays(7)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")) + "\n\n" +
                "Enjoy your shopping!";

        emailService.sendEmail(request.getEmail(), "Coupon Discount", message);
    }

    public void update(@Valid CouponRequestDTO request, Long id) {
        CouponEntity existing = couponRepository.findById(id)
                .orElseThrow(CouponNotFound::new);

        if (couponRepository.existsByCode(request.getCode())) {
            throw new CouponAlreadyExistsException();
        }

        existing.setCode(request.getCode());
        existing.setExpirationDate(request.getExpirationDate());
        existing.setActive(request.isActive());
        existing.setType(CouponEntity.CouponType.valueOf(request.getCouponType().toUpperCase()));
        existing.setDiscountPercentage(request.getDiscountPercentage());
        CourseEntity courseEntity = courseRepository.findById(request.getCourseId()).
                orElseThrow(CourseNotFoundException::new);
        existing.setCourse(courseEntity);
        couponRepository.save(existing);
    }

    public void delete(Long id) {
        CouponEntity existing = couponRepository.findById(id)
                .orElseThrow(CouponNotFound::new);
        couponRepository.delete(existing);
    }


    public CouponRedeemResponseDTO validateCoupon(@Valid CouponRedeemRequestDTO request) {
        if (request.getCode().equals("WELCOME0000")) {
            return validateWelcomeCoupon(request);
        }
        return validateCourseCoupon(request);

    }


    /// HELPERS

    private CouponRedeemResponseDTO validateWelcomeCoupon(CouponRedeemRequestDTO request) {
        UserEntity user = userRepository.findByEmail(userService.getCurrentUser().getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (user.getCreatedDate().isBefore(LocalDateTime.now().minusDays(7))) {
            throw new CouponExpiredException();
        }
        String coupon = request.getCode();
        CouponEntity entity = couponRepository.findByCode(coupon).orElseThrow(CouponNotFound::new);
        //if temporarily disabled by admin
        if (!entity.isActive()) {
            throw new CouponWelcomeNotActive();
        }

        BigDecimal total = courseRepository.findPriceById(request.getCourseId())
                .orElseThrow(CourseNotFoundException::new);

        BigDecimal discountedPrice = calculateDiscount(total, entity.getDiscountPercentage());

        return CouponRedeemResponseDTO.builder()
                .discountPercentage(entity.getDiscountPercentage()).originalPrice(total)
                .discountedPrice(discountedPrice).build();
    }

    private CouponRedeemResponseDTO validateCourseCoupon(CouponRedeemRequestDTO request) {
        String coupon = request.getCode();

        //throw if coupon not exists
        CouponEntity entity = couponRepository.findByCode(coupon).orElseThrow(CouponNotFound::new);


        //throw if coupon not for given course
        if (entity.getCourse().getId().longValue() != request.getCourseId().longValue()) {
            throw new CouponNotFound();
        }
        //throw if coupon has expired
        if (entity.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new CouponExpiredException();
        }
        //throw temporarily disabled by admin
        if (!entity.isActive()) {
            throw new CouponWelcomeNotActive();
        }
        BigDecimal total = courseRepository.findPriceById(request.getCourseId())
                .orElseThrow(CourseNotFoundException::new);


        BigDecimal discountedPrice = calculateDiscount(total, entity.getDiscountPercentage());


        return CouponRedeemResponseDTO.builder()
                .discountPercentage(entity.getDiscountPercentage()).originalPrice(total)
                .discountedPrice(discountedPrice).build();
    }


    private String generateCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    private BigDecimal calculateDiscount(BigDecimal total, BigDecimal discountPercentage) {
        BigDecimal discount = discountPercentage
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return total.multiply(BigDecimal.ONE.subtract(discount));
    }


}

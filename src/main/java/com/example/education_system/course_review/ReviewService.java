package com.example.education_system.course_review;

import com.example.ericka_j_products.Entity.Product;
import com.example.ericka_j_products.Entity.User;
import com.example.ericka_j_products.Repositories.ProductRepository;
import com.example.ericka_j_products.Repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Service
@RequiredArgsConstructor
@Validated
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    void add(@Valid ReviewNewRequestDTO request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new IllegalArgumentException("product not found"));

        if (reviewRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("user already added review ");
        }

        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new IllegalArgumentException("user not found"));

        ReviewEntity entity = ReviewEntity.builder().rate(request.getRate()).comment(request.getComment()).user(user).product(product).build();
        reviewRepository.save(entity);

    }

    void update(ReviewUpdateRequestDTO request, Long id) {

        ReviewEntity entity = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("review not found"));
        entity.setRate(request.getRate());
        entity.setComment(request.getComment());
        reviewRepository.save(entity);
    }

    void delete(Long id) {
        reviewRepository.deleteById(id);
    }


    List<ReviewResponseDTO> getAll() {
        List<ReviewEntity> result = reviewRepository.findAll();

        return result.stream().map(reviewEntity ->
                ReviewResponseDTO.builder().username(reviewEntity.getUser()
                                .getName()).comment(reviewEntity.getComment()
                        ).rate(reviewEntity.getRate())
                        .approved(reviewEntity.isApproved()).build()).toList();
    }

    List<ReviewResponseDTO> getByProduct(Long id) {
        List<ReviewEntity> result = reviewRepository.findAllByProductId(id);

        return result.stream().map(reviewEntity -> ReviewResponseDTO.builder().
                username(reviewEntity.getUser().getName()).
                comment(reviewEntity.getComment()).
                rate(reviewEntity.getRate()).approved
                        (reviewEntity.isApproved())
                .build()).toList();

    }

    void approveReview(Long id, boolean approve) {
        ReviewEntity entity = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("review not found"));
        entity.setApproved(approve);
        reviewRepository.save(entity);

    }


}

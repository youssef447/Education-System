package com.example.education_system.course_review;

import com.example.education_system.course.entity.CourseEntity;
import com.example.education_system.course.repository.CourseRepository;
import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.repository.UserRepository;
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
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;


    void add(@Valid ReviewNewRequestDTO request) {
        CourseEntity course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("course not found"));

        if (reviewRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("user already added review ");
        }

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        ReviewEntity entity = ReviewEntity.builder()
                .rate(request.getRate())
                .comment(request.getComment()).user(user).course(course).build();
        reviewRepository.save(entity);

    }

    void update(ReviewUpdateRequestDTO request, Long id) {

        ReviewEntity entity = reviewRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("review not found"));
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
                                .getUsername()).comment(reviewEntity.getComment()
                        ).rate(reviewEntity.getRate())
                        .approved(reviewEntity.isApproved()).build()).toList();
    }

    List<ReviewResponseDTO> getByCourse(Long id) {
        List<ReviewEntity> result = reviewRepository.findAllByCourseId(id);
        return result.stream().map(reviewEntity -> ReviewResponseDTO.builder().
                username(reviewEntity.getUser().getUsername()).
                comment(reviewEntity.getComment()).
                rate(reviewEntity.getRate()).approved
                        (reviewEntity.isApproved())
                .build()).toList();

    }

    void approveReview(Long id, boolean approve) {
        ReviewEntity entity = reviewRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("review not found"));
        entity.setApproved(approve);
        reviewRepository.save(entity);

    }


}

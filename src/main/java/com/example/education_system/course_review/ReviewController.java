package com.example.education_system.course_review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/add")
    ResponseEntity<Map<String, String>> add(@RequestBody ReviewNewRequestDTO request) {
        reviewService.add(request);
        return ResponseEntity.ok().body(Map.of("message", "review submitted successfully"));
    }


    @PostMapping("/update")
    ResponseEntity<Map<String, String>> update(@RequestBody ReviewUpdateRequestDTO request, @RequestParam Long id) {
        reviewService.update(request, id);
        return ResponseEntity.ok().body(Map.of("message", "review updated successfully"));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    ResponseEntity<Map<String, String>> delete(@RequestParam Long id) {
        reviewService.delete(id);
        return ResponseEntity.ok().body(Map.of("message", "review deleted successfully"));
    }

    @PostMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Map<String, List<ReviewResponseDTO>>> getAll() {
        List<ReviewResponseDTO> reviews = reviewService.getAll();
        return ResponseEntity.ok().body(Map.of("data", reviews));
    }

    @PostMapping("/get")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Map<String, List<ReviewResponseDTO>>> getByCourse(@RequestParam Long id) {
        List<ReviewResponseDTO> reviews = reviewService.getByCourse(id);
        return ResponseEntity.ok().body(Map.of("data", reviews));
    }

    @PostMapping("/approve")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Map<String, String>> approveReview(@RequestParam Long id, @RequestParam boolean approve) {
        reviewService.approveReview(id, approve);
        return ResponseEntity.ok().body(Map.of("message", "review approval updated successfully"));
    }
}

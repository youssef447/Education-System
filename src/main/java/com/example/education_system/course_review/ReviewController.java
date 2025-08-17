package com.example.education_system.course_review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    ResponseEntity<Map<String, String>> add(@RequestBody ReviewNewRequestDTO request) {
        reviewService.add(request);
        return ResponseEntity.ok(Map.of("message", "review submitted successfully"));
    }

    @PutMapping("/{id}")
    ResponseEntity<Map<String, String>> update(@RequestBody ReviewUpdateRequestDTO request, @PathVariable Long id) {
        reviewService.update(request, id);
        return ResponseEntity.ok(Map.of("message", "review updated successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.ok(Map.of("message", "review deleted successfully"));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Map<String, List<ReviewResponseDTO>>> getAll() {
        List<ReviewResponseDTO> reviews = reviewService.getAll();
        return ResponseEntity.ok(Map.of("data", reviews));
    }

    @GetMapping("/course/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Map<String, List<ReviewResponseDTO>>> getByCourse(@PathVariable Long id) {
        List<ReviewResponseDTO> reviews = reviewService.getByCourse(id);
        return ResponseEntity.ok(Map.of("data", reviews));
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Map<String, String>> approveReview(@PathVariable Long id, @RequestParam boolean approve) {
        reviewService.approveReview(id, approve);
        return ResponseEntity.ok(Map.of("message", "review approval updated successfully"));
    }
}

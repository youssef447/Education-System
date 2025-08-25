package com.example.education_system.lesson_progress;


import com.example.education_system.course_lesson.LessonEntity;
import com.example.education_system.course_lesson.LessonRepository;
import com.example.education_system.enrollment.entity.EnrollmentEntity;
import com.example.education_system.enrollment.repository.EnrollmentRepository;

import com.example.education_system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonProgressService {
    private final UserService userService;

    private final LessonProgressMapper progressMapper;
    private final LessonProgressRepository progressRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;

    /**
     * Admin: fetch all lesson progress records, paged
     */
    @Transactional(readOnly = true)
    public List<LessonProgressDTO> getAll(Long enrollmentId, Integer page, Integer size) {
        Pageable pageable = buildPageable(page, size);
        Page<LessonProgressEntity> paged =
                progressRepository.findByEnrollmentId(enrollmentId, pageable);
        return paged.stream()
                .map(progressMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<LessonProgressDTO> getByEnrollment(Long enrollmentId, Integer page, Integer size) {
        EnrollmentEntity enrollment = fetchAndAuthorize(enrollmentId);
        Pageable pageable = buildPageable(page, size);
        Page<LessonProgressEntity> paged =
                progressRepository.findByEnrollmentId(enrollment.getId(), pageable);
        return paged.stream()
                .map(progressMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Mark a lesson as started (or create the record if missing)
     */
    @Transactional
    public LessonProgressDTO markStarted(Long enrollmentId, Long lessonId) {
        EnrollmentEntity enrollment = fetchAndAuthorize(enrollmentId);
        LessonEntity lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
        LessonProgressEntity progress = progressRepository
                .findByEnrollmentIdAndLessonId(enrollmentId, lessonId)
                .orElseGet(() -> {
                    var lp = new LessonProgressEntity();
                    lp.setEnrollment(enrollment);
                    lp.setLesson(lesson);
                    lp.setStatus(LessonProgressEntity.Status.NOT_STARTED);
                    return lp;
                });

        if (progress.getStatus() == LessonProgressEntity.Status.NOT_STARTED) {
            progress.setStatus(LessonProgressEntity.Status.IN_PROGRESS);
            progress.setStartedAt(LocalDateTime.now());
        }

        var saved = progressRepository.save(progress);
        return progressMapper.toDTO(saved);
    }


    @Transactional
    public LessonProgressDTO markCompleted(Long enrollmentId, Long lessonId) {
        EnrollmentEntity enrollment = fetchAndAuthorize(enrollmentId);
        LessonProgressEntity progress = progressRepository
                .findByEnrollmentIdAndLessonId(enrollmentId, lessonId)
                .orElseThrow(() -> new IllegalArgumentException("LessonProgress not found"));

        if (progress.getStatus() != LessonProgressEntity.Status.COMPLETED) {
            if (progress.getStartedAt() == null) {
                progress.setStartedAt(LocalDateTime.now());
            }
            progress.setStatus(LessonProgressEntity.Status.COMPLETED);
            progress.setCompletedAt(LocalDateTime.now());
        }

        progressRepository.save(progress);

        // update enrollment-level progress
        int newPercentage = calcProgress(enrollment.getId());
        enrollment.setProgressPercent(newPercentage);
        if (newPercentage == 100) {
            enrollment.setCompletedAt(LocalDateTime.now());
            enrollment.setStatus(EnrollmentEntity.EnrollmentStatus.COMPLETED);

        }
        enrollmentRepository.save(enrollment);

        return progressMapper.toDTO(progress);
    }


    @Transactional(readOnly = true)
    public LessonProgressDTO getProgress(Long enrollmentId, Long lessonId) {
        fetchAndAuthorize(enrollmentId);
        LessonProgressEntity progress = progressRepository
                .findByEnrollmentIdAndLessonId(enrollmentId, lessonId)
                .orElseThrow(() -> new IllegalArgumentException("LessonProgress not found"));
        return progressMapper.toDTO(progress);
    }

    //--- Helpers ---------------------------------------------------------------

    private Pageable buildPageable(Integer page, Integer size) {
        int p = (page != null && page > 0) ? page : 0;
        int s = (size != null && size > 0) ? size : 20;
        return PageRequest.of(p, s);
    }


    private EnrollmentEntity fetchAndAuthorize(Long enrollmentId) {
        EnrollmentEntity enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
        Long currentUserId = userService.getCurrentUser().getId();
        if (!enrollment.getStudent().getId().equals(currentUserId)) {
            throw new AccessDeniedException("You are not allowed to access this enrollment");
        }
        return enrollment;
    }


    private int calcProgress(Long enrollmentId) {
        return progressRepository.countCompletedLessonsByEnrollmentId(enrollmentId);
    }

}


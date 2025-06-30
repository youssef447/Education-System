package com.example.education_system.courses.service;

import com.example.education_system.courses.dto.CourseResponseDto;
import com.example.education_system.courses.entity.CourseEntity;
import com.example.education_system.courses.mapper.CourseMapper;
import com.example.education_system.courses.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

   public List<CourseResponseDto> getAllCourses() {
        List<CourseEntity> courses = courseRepository.findAll();
        return courseMapper.toListDto(courses);
    }
}

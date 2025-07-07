package com.example.education_system.course_lesson;
import com.example.education_system.config.response.ApiResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;


    List<> getAll() {

    }



    ApiResponseBody add() {
        return new ApiResponseBody("lesson added successfully", true);
    }


    ApiResponseBody update() {
        return new ApiResponseBody("lesson updated successfully", true);
    }


    ApiResponseBody delete(Long id) {
        return new ApiResponseBody("lesson deleted successfully", true);
    }
}

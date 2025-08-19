package com.example.education_system.config.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponseBody {
    private final LocalDateTime time;
    private final String message;
    private Object data;
    private final boolean success;

    public ApiResponseBody(String message, Object data, boolean success) {
        this.time = LocalDateTime.now();
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public ApiResponseBody(String message, boolean success) {
        this.time = LocalDateTime.now();
        this.message = message;
        this.success = success;
    }
}

//TODO: Refactor Response
/*
public record ApiResponse<T>(
        T data,
        boolean result
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, true);
    }

    public static <T> ApiResponse<T> failure() {
        return new ApiResponse<>(null, false);
    }
}*/

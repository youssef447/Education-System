package com.example.education_system.reports;


import com.example.education_system.category.CategoryResponseDto;

public record CategoryStatsDTO(CategoryResponseDto category,
                               Long totalQuantity,
                               Double totalRevenue) {
}

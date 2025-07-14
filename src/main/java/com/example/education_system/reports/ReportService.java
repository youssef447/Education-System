package com.example.education_system.reports;

import com.example.education_system.category.CategoryResponseDto;
import com.example.education_system.course.dto.CourseResponseDto;
import com.example.education_system.order.OrderEntity;
import com.example.education_system.order.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final OrderRepository orderRepository;
    private final ReportPdfGeneratorService reportPdfGeneratorService;

    Long getTotalOrders(String status) {
        return orderRepository.countByStatus(OrderEntity.OrderStatus.valueOf(status));
    }


    public List<CourseStatsDTO> getTopSalesByCourse() {
        List<Object[]> raw = orderRepository.findTopSellingCoursesWithStats();

        return raw.stream()
                .map(row -> new CourseStatsDTO(
                        (CourseResponseDto) row[0],
                        (Long) row[1],
                        (Double) row[2]
                ))
                .toList();
    }

    public List<CategoryStatsDTO> getTopSalesByCategory() {
        List<Object[]> raw = orderRepository.findTopSellingCategoriesWithStats();

        return raw.stream()
                .map(row -> new CategoryStatsDTO(
                        (CategoryResponseDto) row[0],
                        (Long) row[1],
                        (Double) row[2]
                ))
                .toList();
    }


    public Double getTodaySales() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1).minusNanos(1);
        return orderRepository.getTodaySales(start, end)
                .orElse(0.0);
    }

    public Double getThisWeekSales() {
        LocalDateTime start = LocalDate.now()
                .with(java.time.DayOfWeek.MONDAY)
                .atStartOfDay();
        LocalDateTime end = start.plusDays(7).minusNanos(1);
        return orderRepository.getThisWeekSales(start, end)
                .orElse(0.0);
    }

    public Double getThisMonthSales() {
        LocalDateTime start = LocalDate.now()
                .withDayOfMonth(1)
                .atStartOfDay();
        LocalDateTime end = start.plusMonths(1).minusNanos(1);
        return orderRepository.getThisMonthSales(start, end)
                .orElse(0.0);
    }

    public Double getThisYearSales() {
        LocalDateTime start = LocalDate.now()
                .withDayOfYear(1)
                .atStartOfDay();
        LocalDateTime end = start.plusYears(1).minusNanos(1);
        return orderRepository.getThisYearSales(start, end)
                .orElse(0.0);
    }

    Double getDateRangeSales(LocalDateTime startDate,
                             LocalDateTime endDate) {
        return orderRepository.getDateRangeSales(startDate, endDate)
                .orElse(0.0);

    }

    //PDF
    public byte[] topSalesByCoursePdf() {
        List<CourseStatsDTO> result = getTopSalesByCourse();
        return reportPdfGeneratorService.generateTopSalesByCoursePdf(result);
    }

    public byte[] topSalesByCategoryPdf() {
        List<CategoryStatsDTO> result = getTopSalesByCategory();
        return reportPdfGeneratorService.generateTopSalesByCategoryPdf(result);
    }

    // PDF: Today Sales
    public byte[] todaySalesPdf() {
        Double value = getTodaySales(); // service method
        return reportPdfGeneratorService.generateTodaySalesPdf(value);
    }

    // PDF: This Week Sales
    public byte[] thisWeekSalesPdf() {
        Double value = getThisWeekSales(); // service method
        return reportPdfGeneratorService.generateThisWeekSalesPdf(value);
    }

    // PDF: This Month Sales
    public byte[] thisMonthSalesPdf() {
        Double value = getThisMonthSales(); // service method
        return reportPdfGeneratorService.generateThisMonthSalesPdf(value);
    }

    // PDF: This Year Sales
    public byte[] thisYearSalesPdf() {
        Double value = getThisYearSales(); // service method
        return reportPdfGeneratorService.generateThisYearSalesPdf(value);
    }

    // PDF: Date Range Sales
    public byte[] dateRangeSalesPdf(LocalDateTime startDate, LocalDateTime endDate) {
        Double value = getDateRangeSales(startDate, endDate); // service method
        return reportPdfGeneratorService.generateDateRangeSalesPdf(value, startDate, endDate);
    }


}

package com.example.education_system.reports;

import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/getTotalOrders")
    Map<String, Long> getTotalOrders(@RequestParam String status) {
        Long result = reportService.getTotalOrders(status);
        return Map.of("data", result);

    }

    @GetMapping("/getTopSalesByCourse")
    public Map<String, List<CourseStatsDTO>> getTopSalesByCourse() {
        List<CourseStatsDTO> result = reportService.getTopSalesByCourse();
        return Map.of("data", result);

    }

    @GetMapping("/getTopSalesByCategory")
    public Map<String, List<CategoryStatsDTO>> getTopSalesByCategory() {

        List<CategoryStatsDTO> result = reportService.getTopSalesByCategory();
        return Map.of("data", result);

    }


    @GetMapping("/getThisYearSales")
    Map<String, Double> getThisYearSales() {
        Double result = reportService.getThisYearSales();
        return Map.of("data", result);

    }

    @GetMapping("/getThisMonthSales")
    Map<String, Double> getThisMonthSales() {
        Double result = reportService.getThisMonthSales();
        return Map.of("data", result);

    }

    @GetMapping("/getThisWeekSales")
    Map<String, Double> getThisWeekSales() {
        Double result = reportService.getThisWeekSales();
        return Map.of("data", result);

    }

    @GetMapping("/getTodaySales")
    Map<String, Double> getTodaySales() {
        Double result = reportService.getTodaySales();
        return Map.of("data", result);

    }

    @GetMapping("/getDateRangeSales")
    Map<String, Double> getDateRangeSales(@RequestParam LocalDateTime startDate,
                                          @RequestParam LocalDateTime endDate) {
        Double result = reportService.getDateRangeSales(startDate, endDate);
        return Map.of("data", result);

    }

    //pdf

    @GetMapping("/pdf/topSalesByCourse")
    public ResponseEntity<byte[]> topSalesByCoursePdf() throws DocumentException {
        byte[] pdfBytes = reportService.topSalesByCoursePdf();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "top-sales-by-course.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


    @GetMapping("/pdf/topSalesByCategory")
    public ResponseEntity<byte[]> getTopSalesByCategoryPdf() throws DocumentException {
        byte[] pdfBytes = reportService.topSalesByCategoryPdf();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "top-sales-by-category.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/pdf/todaySales")
    public ResponseEntity<byte[]> getTodaySalesPdf() {
        byte[] pdfBytes = reportService.todaySalesPdf();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "today-sales.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/pdf/thisWeekSales")
    public ResponseEntity<byte[]> getThisWeekSalesPdf() {
        byte[] pdfBytes = reportService.thisWeekSalesPdf();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "this-week-sales.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/pdf/thisMonthSales")
    public ResponseEntity<byte[]> getThisMonthSalesPdf() {
        byte[] pdfBytes = reportService.thisMonthSalesPdf();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "this-month-sales.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/pdf/thisYearSales")
    public ResponseEntity<byte[]> getThisYearSalesPdf() {
        byte[] pdfBytes = reportService.thisYearSalesPdf();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "this-year-sales.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/pdf/dateRangeSales")
    public ResponseEntity<byte[]> getDateRangeSalesPdf
            (@RequestParam LocalDateTime startDate,
             @RequestParam LocalDateTime endDate) {

        byte[] pdfBytes = reportService.dateRangeSalesPdf(startDate, endDate);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "date-range-sales.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}

package com.example.education_system.reports;

import com.example.education_system.order.OrderRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportPdfGeneratorService {
    private final OrderRepository orderRepository;

    private final Font TITLE_FONT = new Font(Font.HELVETICA, 16, Font.BOLD, Color.black);
    private final Font HEADER_FONT = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);
    private final Font CELL_FONT = new Font(Font.HELVETICA, 12);
    private final Color HEADER_BG = Color.decode("#f1a2b7");


    public byte[] generateTopSalesByCoursePdf(List<CourseStatsDTO> data) {
        String[] headers = {"Course Name", "Quantity", "Total Revenue"};
        List<String[]> rows = data.stream().map(item ->
                new String[]{
                        item.course().courseCode(),
                        String.valueOf(item.totalQuantity()),
                        String.format("%.2f", item.totalRevenue())
                }).toList();

        return generatePdf("Top Sales by Course", headers, rows);
    }

    public byte[] generateTopSalesByCategoryPdf(List<CategoryStatsDTO> data) {
        String[] headers = {"Category Name", "Quantity", "Total Revenue"};
        List<String[]> rows = data.stream().map(item ->
                new String[]{
                        item.category().getName(),
                        String.valueOf(item.totalQuantity()),
                        String.format("%.2f", item.totalRevenue())
                }).toList();

        return generatePdf("Top Sales by Category", headers, rows);
    }

    public byte[] generateTodaySalesPdf(Double salesValue) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1).minusNanos(1);
        List<Object[]> orders = orderRepository.getTodayOrdersWithTotal(start, end);

        String[] headers = {"Order ID", "Total Price"};
        List<String[]> rows = orders.stream()
                .map(o -> new String[]{
                        String.valueOf(o[0]),
                        String.format("%.2f", (Double) o[1])
                }).toList();

        return generatePdf("Today's Sales Report: " + salesValue, headers, rows);
    }

    public byte[] generateThisWeekSalesPdf(Double salesValue) {
        LocalDateTime start = LocalDate.now()
                .with(java.time.DayOfWeek.MONDAY)
                .atStartOfDay();
        LocalDateTime end = start.plusDays(7).minusNanos(1);
        List<Object[]> orders = orderRepository.getThisWeekOrdersWithTotal(start, end);

        String[] headers = {"Order ID", "Total Price"};
        List<String[]> rows = orders.stream()
                .map(o -> new String[]{
                        String.valueOf(o[0]),
                        String.format("%.2f", (Double) o[1])
                }).toList();

        return generatePdf("This Week's Sales Report: " + salesValue, headers, rows);
    }

    public byte[] generateThisMonthSalesPdf(Double salesValue) {
        LocalDateTime start = LocalDate.now()
                .withDayOfMonth(1)
                .atStartOfDay();
        LocalDateTime end = start.plusMonths(1).minusNanos(1);
        List<Object[]> orders = orderRepository.getThisMonthOrdersWithTotal(start, end);

        String[] headers = {"Order ID", "Total Price"};
        List<String[]> rows = orders.stream()
                .map(o -> new String[]{
                        String.valueOf(o[0]),
                        String.format("%.2f", (Double) o[1])
                }).toList();

        return generatePdf("This Month's Sales: " + salesValue, headers, rows);
    }

    public byte[] generateThisYearSalesPdf(Double salesValue) {
        LocalDateTime start = LocalDate.now()
                .withDayOfYear(1)
                .atStartOfDay();
        LocalDateTime end = start.plusYears(1).minusNanos(1);
        List<Object[]> orders = orderRepository.getThisYearOrdersWithTotal(start, end);

        String[] headers = {"Order ID", "Total Price"};
        List<String[]> rows = orders.stream()
                .map(o -> new String[]{
                        String.valueOf(o[0]),
                        String.format("%.2f", (Double) o[1])
                }).toList();

        return generatePdf("This Year's Sales: " + salesValue, headers, rows);
    }

    public byte[] generateDateRangeSalesPdf(Double salesValue, LocalDateTime start, LocalDateTime end) {
        List<Object[]> orders = orderRepository.getOrdersWithTotalByDateRange(start, end);

        String title = "Sales Report from " + formatDate(start) + " to " + formatDate(end) + ": " + salesValue;
        String[] headers = {"Order ID", "Total Price"};
        List<String[]> rows = orders.stream()
                .map(o -> new String[]{
                        String.valueOf(o[0]),
                        String.format("%.2f", (Double) o[1])
                }).toList();

        return generatePdf(title, headers, rows);
    }


    private String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    private byte[] generatePdf(String title, String[] headers, List<String[]> rows) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph(title, TITLE_FONT));
            document.add(Chunk.NEWLINE);

            document.add(buildTable(headers, rows));
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF for: " + title, e);
        }
    }

    private PdfPTable buildTable(String[] headers, List<String[]> rows) throws DocumentException {
        PdfPTable table = new PdfPTable(headers.length);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, HEADER_FONT));
            cell.setBackgroundColor(HEADER_BG);
            cell.setPadding(8);
            table.addCell(cell);
        }

        for (String[] row : rows) {
            for (String cellText : row) {
                PdfPCell cell = new PdfPCell(new Phrase(cellText, CELL_FONT));
                cell.setPadding(6);
                table.addCell(cell);
            }
        }

        return table;
    }
}

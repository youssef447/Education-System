package com.example.education_system.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Long countByStatus(OrderEntity.OrderStatus status);


    @Query("""
                SELECT oi.course, SUM(oi.quantity), SUM(oi.quantity * oi.unitPrice)
                FROM OrderItemEntity oi
                WHERE oi.order.status = 'PAID'
                GROUP BY oi.course
                ORDER BY SUM(oi.quantity) DESC
            """)
    List<Object[]> findTopSellingCoursesWithStats();


    @Query("""
    SELECT c.name, SUM(oi.quantity) as totalQuantity, SUM(oi.quantity * oi.unitPrice) as totalRevenue
    FROM OrderItemEntity oi
    JOIN oi.course.categories c
    WHERE oi.order.status = 'PAID'
    GROUP BY c.name
    ORDER BY totalQuantity DESC
""")
    List<Object[]> findTopSellingCategoriesWithStats();


    @Query("""
    SELECT SUM(oi.quantity * oi.unitPrice)
    FROM OrderItemEntity oi
    WHERE oi.order.status = 'PAID'
    AND oi.order.createdDate BETWEEN :startDate AND :endDate
""")
    Optional<Double> getDateRangeSales(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);

    @Query("""
    SELECT SUM(oi.quantity * oi.unitPrice)
    FROM OrderItemEntity oi
    WHERE oi.order.status = 'PAID'
    AND oi.order.createdDate BETWEEN :start AND :end
""")
    Optional<Double> getTodaySales(@Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end);

    @Query("""
    SELECT SUM(oi.quantity * oi.unitPrice)
    FROM OrderItemEntity oi
    WHERE oi.order.status = 'PAID'
    AND oi.order.createdDate BETWEEN :start AND :end
""")
    Optional<Double> getThisWeekSales(@Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end);

    @Query("""
    SELECT SUM(oi.quantity * oi.unitPrice)
    FROM OrderItemEntity oi
    WHERE oi.order.status = 'PAID'
    AND oi.order.createdDate BETWEEN :start AND :end
""")
    Optional<Double> getThisMonthSales(@Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end);

    @Query("""
    SELECT SUM(oi.quantity * oi.unitPrice)
    FROM OrderItemEntity oi
    WHERE oi.order.status = 'PAID'
    AND oi.order.createdDate BETWEEN :start AND :end
""")
    Optional<Double> getThisYearSales(@Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end);

    @Query("""
    SELECT oi.order.id, SUM(oi.quantity * oi.unitPrice)
    FROM OrderItemEntity oi
    WHERE oi.order.status = 'PAID'
    AND oi.order.createdDate BETWEEN :start AND :end
    GROUP BY oi.order.id
""")
    List<Object[]> getTodayOrdersWithTotal(@Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);

    @Query("""
    SELECT oi.order.id, SUM(oi.quantity * oi.unitPrice)
    FROM OrderItemEntity oi
    WHERE oi.order.status = 'PAID'
    AND oi.order.createdDate BETWEEN :start AND :end
    GROUP BY oi.order.id
""")
    List<Object[]> getThisWeekOrdersWithTotal(@Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end);

    @Query("""
    SELECT oi.order.id, SUM(oi.quantity * oi.unitPrice)
    FROM OrderItemEntity oi
    WHERE oi.order.status = 'PAID'
    AND oi.order.createdDate BETWEEN :start AND :end
    GROUP BY oi.order.id
""")
    List<Object[]> getThisMonthOrdersWithTotal(@Param("start") LocalDateTime start,
                                               @Param("end") LocalDateTime end);

    @Query("""
    SELECT oi.order.id, SUM(oi.quantity * oi.unitPrice)
    FROM OrderItemEntity oi
    WHERE oi.order.status = 'PAID'
    AND oi.order.createdDate BETWEEN :start AND :end
    GROUP BY oi.order.id
""")
    List<Object[]> getThisYearOrdersWithTotal(@Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end);

    @Query("""
    SELECT oi.order.id, SUM(oi.quantity * oi.unitPrice)
    FROM OrderItemEntity oi
    WHERE oi.order.status = 'PAID'
    AND oi.order.createdDate BETWEEN :startDate AND :endDate
    GROUP BY oi.order.id
""")
    List<Object[]> getOrdersWithTotalByDateRange(@Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);



}

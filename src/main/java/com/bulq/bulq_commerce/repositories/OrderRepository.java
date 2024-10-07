package com.bulq.bulq_commerce.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bulq.bulq_commerce.models.Order;
import com.bulq.bulq_commerce.util.constants.Status;

public interface OrderRepository extends JpaRepository<Order, Long> {
        @Query("SELECT o FROM Order o WHERE " +
                        "(:id IS NULL OR o.id = :id) " + // Direct comparison for id
                        "OR (:status IS NULL OR o.status LIKE CONCAT('%', :status, '%')) " + // Filter by order status
                        "OR (:customerName IS NULL OR o.customerName LIKE CONCAT('%', :customerName, '%')) " + // Filter
                                                                                                               // by
                                                                                                               // customer
                                                                                                               // name
                        "OR (:receiptNumber IS NULL OR o.receiptNumber LIKE CONCAT('%', :receiptNumber, '%'))") // Filter
                                                                                                                // by
                                                                                                                // receipt
                                                                                                                // number
        Page<Order> findByOrderInfo(
                        @Param("id") Long id,
                        @Param("status") String status,
                        @Param("customerName") String customerName,
                        @Param("receiptNumber") String receiptNumber,
                        Pageable pageable);

        @Query("SELECT FUNCTION('MONTHNAME', b.createdAt) AS month, " +
                        "YEAR(b.createdAt) AS year, " +
                        "DAY(b.createdAt) AS day, " +
                        "b.status AS status, " +
                        "COUNT(b) AS total " +
                        "FROM Order b " +
                        "WHERE (:statuses IS NULL OR b.status IN :statuses) " +
                        "AND (:month IS NULL OR FUNCTION('MONTHNAME', b.createdAt) = :month) " +
                        "AND (:year IS NULL OR YEAR(b.createdAt) = :year) " +
                        "AND (:day IS NULL OR DAY(b.createdAt) = :day) " +
                        "GROUP BY FUNCTION('MONTHNAME', b.createdAt), YEAR(b.createdAt), DAY(b.createdAt), b.status " +
                        "ORDER BY year DESC, month ASC, day ASC")
        List<Object[]> findOrderSummaryByDayMonthYearAndStatuses(
                        @Param("statuses") List<Status> statuses,
                        @Param("day") Integer day,
                        @Param("month") String month,
                        @Param("year") Integer year);

        @Query("SELECT FUNCTION('MONTHNAME', b.createdAt) AS month, " +
                        "YEAR(b.createdAt) AS year, " +
                        "DAY(b.createdAt) AS day, " +
                        "b.status AS status, " +
                        "COUNT(b) AS totalItems, " + // Total number of items
                        "SUM(b.subtotal) AS totalShippingAmount " + // Total shipping amount
                        "FROM Order b " +
                        "WHERE (:statuses IS NULL OR b.status IN :statuses) " +
                        "AND (:month IS NULL OR FUNCTION('MONTHNAME', b.createdAt) = :month) " +
                        "AND (:year IS NULL OR YEAR(b.createdAt) = :year) " +
                        "AND (:day IS NULL OR DAY(b.createdAt) = :day) " +
                        "GROUP BY FUNCTION('MONTHNAME', b.createdAt), YEAR(b.createdAt), DAY(b.createdAt), b.status " +
                        "ORDER BY year DESC, month ASC, day ASC")
        List<Object[]> findOrderSummary(
                        @Param("month") String month,
                        @Param("year") Integer year,
                        @Param("day") Integer day,
                        @Param("statuses") List<Status> statuses);

                        @Query("SELECT FUNCTION('MONTHNAME', o.createdAt) AS month, " +
                        "YEAR(o.createdAt) AS year, " +
                        "DAY(o.createdAt) AS day, " +
                        "o.status AS status, " +
                        "COUNT(o) AS totalItems, " +
                        "SUM(o.subtotal) AS totalEarnings " + // Earnings based on subtotal
                        "FROM Order o " +
                        "WHERE o.business.id = :businessId " + // Filtering by business_id
                        "AND (:statuses IS NULL OR o.status IN :statuses) " + // Filtering by a list of statuses
                        "AND (:month IS NULL OR FUNCTION('MONTHNAME', o.createdAt) = :month) " +
                        "AND (:year IS NULL OR YEAR(o.createdAt) = :year) " +
                        "AND (:day IS NULL OR DAY(o.createdAt) = :day) " +
                        "GROUP BY FUNCTION('MONTHNAME', o.createdAt), YEAR(o.createdAt), DAY(o.createdAt), o.status " +
                        "ORDER BY year DESC, month ASC, day ASC")
                 List<Object[]> findOrderSummaryByBusinessId(
                        @Param("businessId") Long businessId,
                        @Param("statuses") List<Status> statuses, // Accept a list of statuses
                        @Param("day") Integer day,
                        @Param("month") String month,
                        @Param("year") Integer year);
                 

}

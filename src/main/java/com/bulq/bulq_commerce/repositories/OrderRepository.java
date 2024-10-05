package com.bulq.bulq_commerce.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bulq.bulq_commerce.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE " +
       "(:id IS NULL OR o.id = :id) " +  // Direct comparison for id
       "OR (:status IS NULL OR o.status LIKE CONCAT('%', :status, '%')) " +  // Filter by order status
       "OR (:customerName IS NULL OR o.customerName LIKE CONCAT('%', :customerName, '%')) " +  // Filter by customer name
       "OR (:receiptNumber IS NULL OR o.receiptNumber LIKE CONCAT('%', :receiptNumber, '%'))")  // Filter by receipt number
Page<Order> findByOrderInfo(
       @Param("id") Long id,
       @Param("status") String status,
       @Param("customerName") String customerName,
       @Param("receiptNumber") String receiptNumber,
       Pageable pageable);
}

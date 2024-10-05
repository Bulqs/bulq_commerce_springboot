package com.bulq.bulq_commerce.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bulq.bulq_commerce.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

       List<Product> findByCollection_id(Long id);

       @Query("SELECT p FROM Product p WHERE " +
       "(:id IS NULL OR p.id = :id) " +  // Direct comparison for id
       "OR (:productName IS NULL OR p.productName LIKE CONCAT('%', :productName, '%')) " +  // Filter by productName
       "OR (:vendor IS NULL OR p.vendor LIKE CONCAT('%', :vendor, '%')) " +  // Filter by vendor
       "OR (:brand IS NULL OR p.brand LIKE CONCAT('%', :brand, '%')) " +  // Filter by brand
       "OR (:category IS NULL OR p.category LIKE CONCAT('%', :category, '%'))")  // Filter by category
Page<Product> findByProductInfo(
       @Param("id") Long id,
       @Param("productName") String productName,
       @Param("vendor") String vendor,
       @Param("brand") String brand,
       @Param("category") String category,
       Pageable pageable);


}

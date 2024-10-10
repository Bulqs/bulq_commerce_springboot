package com.bulq.bulq_commerce.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.bulq.bulq_commerce.util.constants.QualityAssuranceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String productName;

    private String vendor;

    private String brand;

    private String image;

    private Integer price;

    private String material;

    private String description;

    private LocalDateTime createdAt;

    private String category;

    private Integer weight;

    private Integer discount;

    @Enumerated(EnumType.STRING)
    @Column(name = "quality_status", columnDefinition = "enum('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING'")
    private QualityAssuranceType qualityAssurance; // pending, approved, rejected

    private Integer stock;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    
    @CreatedDate
    private LocalDateTime createdDate;

    // Many to one relationship with vendor
    @ManyToOne
    @JoinColumn(name = "collection_id", referencedColumnName = "id", nullable = true)
    private Collection collection;
}

// Many to one relationship with vendor
    // @ManyToOne
    // @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = true)
    // private Business business;

    // @Enumerated(EnumType.STRING)
    // @Column(name = "pickup_type", columnDefinition = "enum('PICKUP', 'HOME') DEFAULT 'PENDING'")
    // private DeliveryType deliveryType;
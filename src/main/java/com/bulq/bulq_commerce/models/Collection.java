package com.bulq.bulq_commerce.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Entity;
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
public class Collection {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;

    private String name;

    private String dsecription;

    private LocalDateTime createdAt;

    private String billboard;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    
    @CreatedDate
    private LocalDateTime createdDate;

    //Many to one relationship with product
    @ManyToOne
    @JoinColumn(name="business_id", referencedColumnName="id", nullable=true)
    private Business business;
}

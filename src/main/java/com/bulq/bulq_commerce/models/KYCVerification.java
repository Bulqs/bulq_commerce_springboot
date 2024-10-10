package com.bulq.bulq_commerce.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.bulq.bulq_commerce.util.constants.KYCType;
import com.bulq.bulq_commerce.util.constants.VerifiedType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class KYCVerification {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_type", columnDefinition = "enum('NIN', 'BVN') DEFAULT 'NIN'")
    private KYCType verificationType;

    private String image;

    private String email;

    private String businessName;

    private LocalDateTime submissionDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    
    @CreatedDate
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "verified_type", columnDefinition = "enum('ISNINVERIFIED', 'ISBVNVERIFIED','ISFULLYVERIFIED') DEFAULT 'ISNINVERIFIED'")
    private VerifiedType verified;

    //one to one relationship with vendor
    @OneToOne
    @JoinColumn(name="business_id", referencedColumnName="id", nullable=true)
    private Business business;
}
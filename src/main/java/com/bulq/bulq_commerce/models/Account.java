package com.bulq.bulq_commerce.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.bulq.bulq_commerce.util.constants.Verification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    // @Column(unique=true)
    private String email;

    private String password;

    private String authorities;

    // @Column(name = "phone")
    private String telephone;

    private String firstName;

    private String lastName;

    private String username;

    private String country;

    private String image;

    private String state;

    private String city;

    private String address;

    private String otp;

    @Enumerated(EnumType.STRING)
    @Column(name = "verified", columnDefinition = "enum('PENDING', 'VERIFIED', 'REJECTED') DEFAULT 'PENDING'")
    private Verification verified;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Column(name = "token")
    private String token;

    // @Column(name = "password_reset_token_expiry", columnDefinition = "TIMESTAMP")
    private LocalDateTime passswordResetTokenExpiry;

    // @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    
    @CreatedDate
    private LocalDateTime createdDate;
    
    @OneToOne(mappedBy = "account")
    private Business business;

    // One-to-one relationship with Wallet (mapped by the 'account' field in Wallet)
    @OneToOne(mappedBy = "account")
    private Wallet wallet;
}
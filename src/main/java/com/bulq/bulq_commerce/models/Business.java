package com.bulq.bulq_commerce.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Business {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;

    private String business_name;

    private String business_location;

    private String business_type;

    private String email;

    private String phone_number;

    private String otp;

    private String agree; //agree disagree

    private Integer earning;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    
    @CreatedDate
    private LocalDateTime createdDate;

    //One to one relationship with user 
    
    // @OneToOne(cascade = CascadeType.ALL)
    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    // One-to-one relationship with Txn. (mapped by the 'booking' field in Transaction)
    @OneToOne(mappedBy = "business")
    private KYCVerification kyc;
}

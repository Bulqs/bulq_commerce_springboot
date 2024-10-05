package com.bulq.bulq_commerce.models;

import java.time.LocalDateTime;

import com.bulq.bulq_commerce.util.constants.DeliveryType;
import com.bulq.bulq_commerce.util.constants.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "customer_order") 
public class Order {
    @Id
    // @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
    private long id;

    private Integer totalItems;

    private Integer subtotal;

    private Integer shippingFee;

    private LocalDateTime createdAt;

    private LocalDateTime dateOfDelivery;

    private Status status;

    private String customerName;

    private String receiptNumber;

    private String country;

    private String state;

    private String address;

    private DeliveryType deliveryType;

    // Many to one relationship with user and account
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = true)
    private Account account;

    // Many to one relationship with user and account
    @ManyToOne
    @JoinColumn(name = "business_id", referencedColumnName = "id", nullable = true)
    private Business business;
}

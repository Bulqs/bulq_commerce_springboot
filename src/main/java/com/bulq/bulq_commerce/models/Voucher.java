package com.bulq.bulq_commerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Voucher {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;

    private String image;

    private String brand;

    private String description;

    private Integer amount;

    private Integer discount;

    //Many to one relationship with user
}

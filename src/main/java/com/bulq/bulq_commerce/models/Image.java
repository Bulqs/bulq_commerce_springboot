package com.bulq.bulq_commerce.models;

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
public class Image {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;

    private String image;

    //Many to one relationship with product and vendor
    @ManyToOne
    @JoinColumn(name="productImage_id", referencedColumnName="id", nullable=true)
    private Product product;
}

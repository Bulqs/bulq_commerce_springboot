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
public class Size {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;

    private String size;

    //Many to one relationship with product
    @ManyToOne
    @JoinColumn(name="product_id", referencedColumnName="id", nullable=true)
    private Product product;
}

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
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;

    private String image;

    private String color;

    private String size;

    private Integer frequency;

    private Integer total;

    private Integer discount;

    private String vendor;

    // private String status;

    //Many to one relationship with order
    @ManyToOne
    @JoinColumn(name="order_id", referencedColumnName="id", nullable=true)
    private Order order;
}

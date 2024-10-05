package com.bulq.bulq_commerce.payload.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductViewDTO {

    private Long id;

    private String productName;

    private String vendor;

    private String brand;

    private Integer price;

    private String image;

    private String material;

    private String description;

    private String category;

    private Integer weight;

    private Integer discount;

    private Integer stock;
}
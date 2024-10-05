package com.bulq.bulq_commerce.payload.products;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleProductViewDTO {

    private Long id;

    private String productName;

    private String vendor;

    private String brand;

    private Integer price;

    private String material;

    private String description;

    private String category;

    private Integer weight;

    private String image;

    private Integer discount;

    private Integer stock;

    private List<ColorViewDTO> colors;

    private List<SizeViewDTO> sizes;

    private List<ImageViewDTO> images;
}
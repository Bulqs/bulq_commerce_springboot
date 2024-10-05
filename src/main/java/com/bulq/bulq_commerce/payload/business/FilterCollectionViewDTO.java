package com.bulq.bulq_commerce.payload.business;

import java.util.List;

import com.bulq.bulq_commerce.payload.products.FilterProductViewDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterCollectionViewDTO {

    private long id;

    private String name;

    private String description;

    private String billboard;

    List<FilterProductViewDTO> products;

}
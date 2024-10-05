package com.bulq.bulq_commerce.payload.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemViewDTO {

    private Long id;
    
    private String image;

    private String color;

    private String size;

    private Integer frequency;

    private Integer total;

    private Integer discount;

    private String vendor;
}

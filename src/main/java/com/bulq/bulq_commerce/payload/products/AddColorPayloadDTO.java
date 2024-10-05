package com.bulq.bulq_commerce.payload.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddColorPayloadDTO {

    private Long productId;
    
    private String color;

}
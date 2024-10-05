package com.bulq.bulq_commerce.payload.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddBusinessImagePayloadDTO {

    private Long productId;

    private Long collectionId;

    private String image;

}
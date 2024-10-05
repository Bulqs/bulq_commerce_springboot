package com.bulq.bulq_commerce.payload.order;

import com.bulq.bulq_commerce.util.constants.DeliveryType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterOrderViewDT {

    private Long id;
    
    private Integer totalItems;

    private Integer subtotal;

    private Integer shippingFee;

    private String customerName;

    private String country;

    private String state;

    private String address;

    private DeliveryType deliveryType;
}

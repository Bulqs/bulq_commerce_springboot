package com.bulq.bulq_commerce.payload.order;

import com.bulq.bulq_commerce.util.constants.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryAmountDTO {
    
    private String month;
    private Integer year;
    private Integer day;
    private Status status;
    private Long totalItems;  // Total number of items for the given status
    private Double totalShippingAmount;
}

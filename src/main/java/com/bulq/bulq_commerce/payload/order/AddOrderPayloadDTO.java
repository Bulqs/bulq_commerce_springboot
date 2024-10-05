package com.bulq.bulq_commerce.payload.order;

import java.util.List;

import com.bulq.bulq_commerce.util.constants.DeliveryType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderPayloadDTO {
    
    private Long productId;
    
    @Schema(description = "input totalItems", defaultValue = "1")
    private Integer totalItems;

    @Schema(description = "input subtotal", defaultValue = "44")
    private Integer subtotal;

    @Schema(description = "input shippingFee", defaultValue = "300")
    private Integer shippingFee;

    @NotBlank
    @Schema(description = "input customerName", defaultValue = "Olahammed")
    private String customerName;

    @NotBlank
    @Schema(description = "input country", defaultValue = "country")
    private String country;

    @NotBlank
    @Schema(description = "input state", defaultValue = "Lagos")
    private String state;

    @NotBlank
    @Schema(description = "input address", defaultValue = "10, Ademola Ojo.")
    private String address;

    @Schema(description = "input type of delivery", defaultValue = "HOME OR PICKUP")
    private DeliveryType deliveryType;

    @Schema(description = "input array of items")
    List<AddItemPayloadDTO> items;
}

/**
 * 
 * 
 * private Integer totalItems;

    private Integer subtotal;

    private Integer shippingFee;

    private String customerName;

    private String receiptNumber;

    private String country;

    private String state;

    private String address;
 */

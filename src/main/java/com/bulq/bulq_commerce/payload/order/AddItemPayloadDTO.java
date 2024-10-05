package com.bulq.bulq_commerce.payload.order;

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
public class AddItemPayloadDTO {
    
    @Schema(description = "input image", defaultValue = "")
    private String image;

    @NotBlank
    @Schema(description = "input color", defaultValue = "Brown")
    private String color;

    @NotBlank
    @Schema(description = "input size", defaultValue = "XL")
    private String size;

    @NotBlank
    @Schema(description = "input frequency", defaultValue = "123")
    private String frequency;

    @NotBlank
    @Schema(description = "input total", defaultValue = "123")
    private String total;

    @NotBlank
    @Schema(description = "input discount", defaultValue = "12")
    private String discount;

    @NotBlank
    @Schema(description = "input vendor", defaultValue = "Bulq")
    private String vendor;

    // @NotBlank
    // @Schema(description = "input status", defaultValue = "12")
    // private String status;
}

/**
 * 
 * private String image;

    private String color;

    private String size;

    private Integer frequency;

    private Integer total;

    private Integer discount;

    private String vendor;

    private String status;
 */

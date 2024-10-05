package com.bulq.bulq_commerce.payload.business;

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
public class UpdateBusinessProductPayloadDTO {

    private Long collectionId;

    private Long productId;

    @NotBlank
    @Schema(defaultValue = "productName", description = "Input productName")
    private String productName;

    @NotBlank
    @Schema(defaultValue = "brand", description = "Input brand")
    private String brand;

    @NotBlank
    @Schema(defaultValue = "price", description = "Input price")
    private Integer price;

    @NotBlank
    @Schema(defaultValue = "material", description = "Input material")
    private String material;

    @Schema(defaultValue = "image", description = "Input image")
    private String image;

    @NotBlank
    @Schema(defaultValue = "description", description = "Input description")
    private String description;

    @NotBlank
    @Schema(defaultValue = "category", description = "Input category")
    private String category;

    @NotBlank
    @Schema(defaultValue = "weight", description = "Input weight")
    private Integer weight;

    @NotBlank
    @Schema(defaultValue = "0", description = "Input discount")
    private Integer discount;

    @NotBlank
    @Schema(defaultValue = "stock", description = "Input stock")
    private Integer stock;
}
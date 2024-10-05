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
public class AddBusinessPayloadDTO {

    @NotBlank
    @Schema(defaultValue = "Bulq", description = "Input business name")
    private String business_name;

    @NotBlank
    @Schema(defaultValue = "Canada", description = "Input business location")
    private String business_location;

    @NotBlank
    @Schema(defaultValue = "business_type", description = "Input business type")
    private String business_type;

    @NotBlank
    @Schema(defaultValue = "email", description = "Input email")
    private String email;

    @NotBlank
    @Schema(defaultValue = "090XXXX", description = "Input phone_number")
    private String phone_number;

    @NotBlank
    @Schema(defaultValue = "productName", description = "Input productName")
    private String agree; // agree disagree

    @NotBlank
    @Schema(defaultValue = "productName", description = "Input productName")
    private Integer earning;

}
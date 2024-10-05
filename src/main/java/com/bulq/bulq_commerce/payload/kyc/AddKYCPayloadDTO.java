package com.bulq.bulq_commerce.payload.kyc;

import com.bulq.bulq_commerce.util.constants.KYCType;

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
public class AddKYCPayloadDTO {

    @Schema(description="input businessId", defaultValue="1")
    private Long businessId;

    @Schema(description = "input verification type", defaultValue = "NIN OR BVN")
    private KYCType verificationType;

    @NotBlank
    @Schema(description = "input image of document", defaultValue = "my name")
    private String image;
}

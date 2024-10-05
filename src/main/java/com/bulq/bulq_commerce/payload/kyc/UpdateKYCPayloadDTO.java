package com.bulq.bulq_commerce.payload.kyc;

import com.bulq.bulq_commerce.util.constants.Verification;
import com.bulq.bulq_commerce.util.constants.VerifiedType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateKYCPayloadDTO {

    @Schema(description="input kycId", defaultValue="1")
    private Long kycId;

    @Schema(description = "input verification status", defaultValue = "ISNINVERIFIED OR ISBVNVERIFIED OR ISFULLYVERIFIED OR ISPENDING")
    private VerifiedType verified;

    @Schema(description = "input verification status", defaultValue = "VERIFIED OR PENDING OR REJECTED")
    private Verification mailMessage;

}

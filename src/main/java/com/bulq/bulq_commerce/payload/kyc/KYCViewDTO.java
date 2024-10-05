package com.bulq.bulq_commerce.payload.kyc;

import com.bulq.bulq_commerce.util.constants.KYCType;
import com.bulq.bulq_commerce.util.constants.VerifiedType;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KYCViewDTO {

    private Long businessId;

    private KYCType verificationType;

    private String image;

    private String email;

    private String businessName;

    private LocalDateTime submissionDate;

    private VerifiedType verified;
}

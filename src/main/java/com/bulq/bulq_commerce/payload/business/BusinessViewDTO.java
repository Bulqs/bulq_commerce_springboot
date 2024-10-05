package com.bulq.bulq_commerce.payload.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessViewDTO {

    private Long id;

    private String business_name;

    private String business_location;

    private String business_type;

    private String email;

    private String phone_number;

    private String agree; // agree disagree

    private Integer earning;

}
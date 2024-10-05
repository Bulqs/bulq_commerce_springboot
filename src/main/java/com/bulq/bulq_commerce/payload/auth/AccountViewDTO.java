package com.bulq.bulq_commerce.payload.auth;

import java.time.LocalDateTime;

import com.bulq.bulq_commerce.util.constants.Verification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountViewDTO {
    
    private Long id;

    private String email;

    private String authorities;

    private LocalDateTime createdAt;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String username;

    private Verification verified;
}

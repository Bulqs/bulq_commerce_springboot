package com.bulq.bulq_commerce.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bulq.bulq_commerce.models.Account;
import com.bulq.bulq_commerce.models.Business;
import com.bulq.bulq_commerce.models.KYCVerification;
import com.bulq.bulq_commerce.payload.kyc.AddKYCPayloadDTO;
import com.bulq.bulq_commerce.payload.kyc.KYCViewDTO;
import com.bulq.bulq_commerce.payload.kyc.UpdateKYCPayloadDTO;
import com.bulq.bulq_commerce.services.AccountService;
import com.bulq.bulq_commerce.services.BusinessService;
import com.bulq.bulq_commerce.services.EmailService;
import com.bulq.bulq_commerce.services.KYCService;
import com.bulq.bulq_commerce.util.constants.EmaiLError;
import com.bulq.bulq_commerce.util.constants.VerifiedType;
import com.bulq.bulq_commerce.util.email.EmailDetailsWelcome;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/kyc")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "KYC Controller", description = "Controller for KYC verifications")
public class KYCController {

    @Autowired
    private final AccountService accountService;

    @Autowired
    private final KYCService kycService;

    @Autowired
    private final BusinessService businessService;

    @Autowired
    private final EmailService emailService;

    @PostMapping("/perform-kyc")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Please pass the correct payload")
    @ApiResponse(responseCode = "201", description = "Kyc done")
    @ApiResponse(responseCode = "200", description = "success")
    @Operation(summary = "Perform KYC")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> performKYC(@Valid @RequestBody AddKYCPayloadDTO payloadDTO,
            Authentication authentication) {
        String email = "";
        email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Business business;
        Account account;
        if (!optionalAccount.isPresent()) {
            ResponseEntity.badRequest().body(null);
        }
        account = optionalAccount.get();
        Optional<Business> optionalBusiness = businessService.findById(account.getBusiness().getId());
        business = optionalBusiness.get();
        if ((account.getId() != business.getAccount().getId()) ||
                (account.getAuthorities() != "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }

        try {
            KYCVerification kyc = new KYCVerification();
            kyc.setBusiness(business);
            kyc.setBusinessName(business.getBusiness_name());
            kyc.setEmail(business.getEmail());
            kyc.setImage(payloadDTO.getImage());
            kyc.setSubmissionDate(LocalDateTime.now());
            kyc.setVerificationType(payloadDTO.getVerificationType());
            kyc.setVerified(VerifiedType.ISPENDING);
            kycService.save(kyc);

            String subject = "Verification awaiting approval";
            EmailDetailsWelcome businessDetails = new EmailDetailsWelcome(business.getEmail(),
                    business.getBusiness_name(),
                    subject, business.getBusiness_name());
            System.out.println(businessDetails);

            if (emailService.sendKYCPendingEmail(businessDetails) == false) {
                log.debug(EmaiLError.Email_Error.toString() + " " + "Error sending mail");
                return ResponseEntity.badRequest().body("Error sending mail, contact admin");
            }
            ;

            return ResponseEntity.ok("KYC done successfully, your verification is under review. check your mail.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/all")
    @ApiResponse(responseCode = "200", description = "List of kycs pagination")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token error")
    @Operation(summary = "List kycs in paginated format")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<List<KYCViewDTO>> allKYCs(
            @RequestParam(required = false, name = "submissionDate", defaultValue = "submissionDate") String sort_by,
            @RequestParam(required = false, name = "per_page", defaultValue = "2") String per_page,
            @RequestParam(required = false, name = "page", defaultValue = "1") String page,
            @RequestParam(required = false, name = "id", defaultValue = "1") Long id,
            @RequestParam(required = false, name = "businessName", defaultValue = "RedOx98") String businessName,
            @RequestParam(required = false, name = "email", defaultValue = "") String email,
            @RequestParam(required = false, name = "verified", defaultValue = "") VerifiedType verified) {
        Page<KYCVerification> kycsOnPage = kycService.findByKYCVerificationInfo(
                Integer.parseInt(page) - 1,
                Integer.parseInt(per_page),
                sort_by,
                id,
                businessName,
                email,
                verified);

        List<KYCVerification> kycList = kycsOnPage.getContent();

        // Total pages calculation
        int totalPages = kycsOnPage.getTotalPages();
        List<Integer> pages = new ArrayList<>();

        if (totalPages > 0) {
            pages = IntStream.rangeClosed(0, totalPages - 1).boxed().collect(Collectors.toList());
        }

        // Constructing the response
        if (!kycList.isEmpty()) {
            List<KYCViewDTO> kycs = kycList.stream()
                    .map(kyc -> new KYCViewDTO(kyc.getId(), kyc.getVerificationType(), kyc.getImage(), kyc.getEmail(),
                            kyc.getBusinessName(), kyc.getSubmissionDate(), kyc.getVerified()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(kycs);
        }

        // Return empty list if no content
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping(value = "/{kycId}/view-kyc", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "View single rating")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token error")
    @Operation(summary = "View single rating")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<KYCViewDTO> viewComplaint(@PathVariable("kycId") Long kycId) {
        Optional<KYCVerification> optionalKyc = kycService.findById(kycId);
        if (!optionalKyc.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        KYCVerification kyc;
        kyc = optionalKyc.get();
        KYCViewDTO kycViewDTO = new KYCViewDTO(kyc.getId(), kyc.getVerificationType(), kyc.getImage(), kyc.getEmail(),
                kyc.getBusinessName(), kyc.getSubmissionDate(), kyc.getVerified());
        return ResponseEntity.ok(kycViewDTO);
    }

    @PutMapping(value = "/update-kyc", produces = "application/json", consumes = "application/json")
    @ApiResponse(responseCode = "200", description = "List of catalogs")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token error")
    @Operation(summary = "update kyc")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<KYCViewDTO> updateKYC(@Valid @RequestBody UpdateKYCPayloadDTO payloadDTO) {
        Optional<KYCVerification> optionalKYC = kycService.findById(payloadDTO.getKycId());
        if (optionalKYC.isPresent()) {
            KYCVerification kyc = optionalKYC.get();
            kyc.setVerified(payloadDTO.getVerified());
            kycService.save(kyc);

            Optional<Business> optionalBusiness = businessService.findById(kyc.getBusiness().getId());
            Business business;
            if (!optionalBusiness.isPresent()) {
                return ResponseEntity.badRequest().body(null);
            }

            business = optionalBusiness.get();

            KYCViewDTO kycViewDTO = new KYCViewDTO(kyc.getId(), kyc.getVerificationType(), kyc.getImage(),
                    kyc.getEmail(), kyc.getBusinessName(), kyc.getSubmissionDate(), kyc.getVerified());
            String subject = "Verification awaiting approval";
            EmailDetailsWelcome businessDetails = new EmailDetailsWelcome(business.getEmail(),
                    business.getBusiness_name(),
                    subject, business.getBusiness_name());
            System.out.println(businessDetails);

            try {
                if (emailService.sendKYCStatusEmail(businessDetails) == false) {
                    log.debug(EmaiLError.Email_Error.toString() + " " + "Error sending mail");

                }
            } catch (MessagingException e) {
                return ResponseEntity.badRequest().body(null);
            }
            ;
            return ResponseEntity.ok(kycViewDTO);

        }
        return ResponseEntity.badRequest().body(null);
    }

}

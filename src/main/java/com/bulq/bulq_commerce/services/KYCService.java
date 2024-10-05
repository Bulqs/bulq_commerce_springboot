package com.bulq.bulq_commerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bulq.bulq_commerce.models.KYCVerification;
import com.bulq.bulq_commerce.repositories.KYCVerificationRepository;
import com.bulq.bulq_commerce.util.constants.VerifiedType;

@Service
public class KYCService {

    @Autowired
    private KYCVerificationRepository kycRepository;
    

    public KYCVerification save(KYCVerification kycVerification) {
        
        return kycRepository.save(kycVerification);
    }

    public Optional<KYCVerification> findById(Long id){
        return kycRepository.findById(id);
    }

    public Page<KYCVerification> findByKYCVerificationInfo(int page, int pageSize, String sortBy, Long id, String businessName, String email, VerifiedType verified){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return kycRepository.findByKYCInfo(id, businessName, email, verified, pageable);
}

}

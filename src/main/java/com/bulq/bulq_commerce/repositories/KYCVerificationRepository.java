package com.bulq.bulq_commerce.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bulq.bulq_commerce.models.KYCVerification;
import com.bulq.bulq_commerce.util.constants.VerifiedType;

public interface KYCVerificationRepository extends JpaRepository<KYCVerification, Long> {
       @Query("SELECT k FROM KYCVerification k WHERE " +
       "(:id IS NULL OR k.id = :id) " +  // Direct comparison for id
       "AND (:businessName IS NULL OR k.businessName LIKE CONCAT('%', :businessName, '%')) " +  // Filter by business name
       "AND (:email IS NULL OR k.email LIKE CONCAT('%', :email, '%')) " +  // Filter by email
       "AND (:verified IS NULL OR k.verified = :verified)")  // Filter by verified status
Page<KYCVerification> findByKYCInfo(
       @Param("id") Long id,
       @Param("businessName") String businessName,
       @Param("email") String email,
       @Param("verified") VerifiedType verified,
       Pageable pageable);

}

package com.bulq.bulq_commerce.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bulq.bulq_commerce.models.Business;

public interface BusinessRepository extends JpaRepository<Business, Long> {
       @Query("SELECT b FROM Business b WHERE " +
                     "(:id IS NULL OR b.id = :id) " + // Direct comparison for id
                     "OR (:business_name IS NULL OR b.business_name LIKE CONCAT('%', :business_name, '%')) " + // Filter
                                                                                                               // by
                                                                                                               // business
                                                                                                               // name
                     "OR (:email IS NULL OR b.email LIKE CONCAT('%', :email, '%')) " + // Filter by email
                     "OR (:business_type IS NULL OR b.business_type LIKE CONCAT('%', :business_type, '%')) " + // Filter
                                                                                                               // by
                                                                                                               // business
                                                                                                               // type
                     "OR (:phone_number IS NULL OR b.phone_number LIKE CONCAT('%', :phone_number, '%'))") // Filter by
                                                                                                          // phone
                                                                                                          // number
       Page<Business> findByBusinessInfo(
                     @Param("id") Long id,
                     @Param("business_name") String business_name,
                     @Param("email") String email,
                     @Param("business_type") String business_type,
                     @Param("phone_number") String phone_number,
                     Pageable pageable);

}

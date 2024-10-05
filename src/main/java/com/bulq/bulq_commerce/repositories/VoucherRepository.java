package com.bulq.bulq_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bulq.bulq_commerce.models.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    
}

package com.bulq.bulq_commerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bulq.bulq_commerce.models.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByWallet_id(Long id);
}

package com.bulq.bulq_commerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bulq.bulq_commerce.models.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    List<Collection> findByBusiness_id(Long id);
}

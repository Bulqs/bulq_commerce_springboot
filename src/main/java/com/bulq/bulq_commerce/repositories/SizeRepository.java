package com.bulq.bulq_commerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bulq.bulq_commerce.models.Size;

public interface SizeRepository extends JpaRepository<Size, Long> {
    List<Size> findByProduct_id(Long id);
}

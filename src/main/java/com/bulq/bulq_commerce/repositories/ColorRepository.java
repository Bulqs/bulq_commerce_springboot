package com.bulq.bulq_commerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bulq.bulq_commerce.models.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {
    List<Color> findByProduct_id(Long id);
}

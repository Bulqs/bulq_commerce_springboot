package com.bulq.bulq_commerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bulq.bulq_commerce.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProduct_id(Long id);
}

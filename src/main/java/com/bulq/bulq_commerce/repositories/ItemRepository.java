package com.bulq.bulq_commerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bulq.bulq_commerce.models.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOrder_id(Long id);
}

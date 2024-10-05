package com.bulq.bulq_commerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulq.bulq_commerce.models.Item;
import com.bulq.bulq_commerce.repositories.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item save(Item Item){
        return itemRepository.save(Item);
    }

    public List<Item> findByOrder_id(Long id) {
        return itemRepository.findByOrder_id(id);
    }
}

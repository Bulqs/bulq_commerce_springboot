package com.bulq.bulq_commerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulq.bulq_commerce.models.Color;
import com.bulq.bulq_commerce.repositories.ColorRepository;

@Service
public class ColorService {
    
    @Autowired
    private ColorRepository colorRepository;

    public Color save(Color color){
        return colorRepository.save(color);
    }

    public List<Color> findByProduct_id(Long id) {
        return colorRepository.findByProduct_id(id);
    }
}

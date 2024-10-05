package com.bulq.bulq_commerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulq.bulq_commerce.models.Size;
import com.bulq.bulq_commerce.repositories.SizeRepository;

@Service
public class SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    public Size save(Size size){
        return sizeRepository.save(size);
    }

    public List<Size> findByProduct_id(Long id) {
        return sizeRepository.findByProduct_id(id);
    }
}

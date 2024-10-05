package com.bulq.bulq_commerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulq.bulq_commerce.models.Image;
import com.bulq.bulq_commerce.repositories.ImageRepository;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image save(Image image){
        return imageRepository.save(image);
    }

    public List<Image> findByProduct_id(Long id) {
        return imageRepository.findByProduct_id(id);
    }
}

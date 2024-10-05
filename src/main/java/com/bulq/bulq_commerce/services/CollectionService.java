package com.bulq.bulq_commerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulq.bulq_commerce.models.Collection;
import com.bulq.bulq_commerce.repositories.CollectionRepository;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;
    

    public Collection save(Collection collection) {
        
        return collectionRepository.save(collection);
    }

    public List<Collection> findByBusiness_id(Long id) {
        return collectionRepository.findByBusiness_id(id);
    }

    public Optional<Collection> findById(Long id){
        return collectionRepository.findById(id);
    }

    public List<Collection> findByBusinessId(Long id){
        return collectionRepository.findByBusiness_id(id);
}

}

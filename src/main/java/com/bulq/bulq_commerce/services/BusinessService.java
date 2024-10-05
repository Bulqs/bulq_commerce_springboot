package com.bulq.bulq_commerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bulq.bulq_commerce.models.Business;
import com.bulq.bulq_commerce.repositories.BusinessRepository;

@Service
public class BusinessService {

    @Autowired
    private BusinessRepository businessRepository;
    

    public Business save(Business Business) {
        
        return businessRepository.save(Business);
    }

    public List<Business> findAll(){
        return businessRepository.findAll();
    }

    public Optional<Business> findById(Long id){
        return businessRepository.findById(id);
    }

    public void deleteById(Long id){
        businessRepository.deleteById(id);
    }

    public Page<Business> findByBusinessInfo(int page, int pageSize, String sortBy, Long id, String BusinessName, String email, String business_type, String phone_number){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return businessRepository.findByBusinessInfo(id, BusinessName, email, business_type, phone_number, pageable);

}
}

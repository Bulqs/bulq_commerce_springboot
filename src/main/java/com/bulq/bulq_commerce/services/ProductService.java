package com.bulq.bulq_commerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bulq.bulq_commerce.models.Product;
import com.bulq.bulq_commerce.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    

    public Product save(Product product) {
        
        return productRepository.save(product);
    }

    public List<Product> findByCollectionId(Long id){
        return productRepository.findByCollection_id(id);
}

    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    public Page<Product> findByProductInfo(int page, int pageSize, String sortBy, Long id, String productName, String vendor, String brand, String category){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return productRepository.findByProductInfo(id, productName, vendor, brand, category, pageable);

}
}

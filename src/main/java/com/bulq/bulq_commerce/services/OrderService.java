package com.bulq.bulq_commerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bulq.bulq_commerce.models.Order;
import com.bulq.bulq_commerce.repositories.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order save(Order Order){
        return orderRepository.save(Order);
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id){
        return orderRepository.findById(id);
    }

    public void deleteById (Long id){
        orderRepository.deleteById(id);
    }

    public Page<Order> findByOrderInfo(int page, int pageSize, String sortBy, Long id,String status, String customerName, String receiptNumber ) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return orderRepository.findByOrderInfo(id, status, customerName, receiptNumber,  pageable);
    }
}

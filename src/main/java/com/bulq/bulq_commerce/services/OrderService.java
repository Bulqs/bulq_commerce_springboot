package com.bulq.bulq_commerce.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bulq.bulq_commerce.models.Order;
import com.bulq.bulq_commerce.payload.order.OrderBusinessSummaryAmountDTO;
import com.bulq.bulq_commerce.payload.order.OrderSummaryAmountDTO;
import com.bulq.bulq_commerce.payload.order.OrderSummaryDTO;
import com.bulq.bulq_commerce.repositories.OrderRepository;
import com.bulq.bulq_commerce.util.constants.Status;

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

    public List<OrderSummaryDTO> getOrderSummary(List<Status> statuses, Integer day, String month, Integer year){
        List<Object[]> results = orderRepository.findOrderSummaryByDayMonthYearAndStatuses(statuses, day, month, year);
        return results.stream().map(result -> new OrderSummaryDTO(
            (String) result[0],   // month
                (Integer) result[1],  // year
                (Integer) result[2],  // day
                (Status) result[3],  // status
                (Long) result[4]   // total number of items
        )).collect(Collectors.toList());
    }

    public List<OrderSummaryAmountDTO> getOrderSummaryWithAmount(List<Status> statuses, Integer day, String month, Integer year) {
        List<Object[]> results = orderRepository.findOrderSummary(month, year, day, statuses);
        
        // Mapping to OrderSummaryAmountDTO
        return results.stream().map(result -> new OrderSummaryAmountDTO(
            (String) result[0],   // month
            (Integer) result[1],  // year
            (Integer) result[2],  // day
            (Status) result[3],   // status
            (Long) result[4],     // total number of items
            (Double) result[5]    // total shipping amount
        )).collect(Collectors.toList());
    }

    public List<OrderBusinessSummaryAmountDTO> getOrderSummaryByBusinessId(Long businessId, List<Status> statuses, Integer day, String month, Integer year) {
        List<Object[]> results = orderRepository.findOrderSummaryByBusinessId(businessId, statuses, day, month, year);
        return results.stream().map(result -> new OrderBusinessSummaryAmountDTO(
                (String) result[0],   // month
                (Integer) result[1],  // year
                (Integer) result[2],  // day
                (Status) result[3],   // status
                (Long) result[4],     // totalItems
                (Double) result[5]    // totalEarnings
        )).collect(Collectors.toList());
    }


    public Page<Order> findByOrderInfo(int page, int pageSize, String sortBy, Long id,String status, String customerName, String receiptNumber ) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return orderRepository.findByOrderInfo(id, status, customerName, receiptNumber,  pageable);
    }
}

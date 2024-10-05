package com.bulq.bulq_commerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulq.bulq_commerce.models.Notification;
import com.bulq.bulq_commerce.repositories.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Notification save(Notification category){
        return notificationRepository.save(category);
    }

    public List<Notification> findByAccount_id(Long id) {
        return notificationRepository.findByAccount_id(id);
    }

    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }

    public Optional<Notification> findById(Long id){
        return notificationRepository.findById(id);
    }

    public void deleteById (Long id){
        notificationRepository.deleteById(id);
    }
}

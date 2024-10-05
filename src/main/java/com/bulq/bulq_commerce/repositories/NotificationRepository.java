package com.bulq.bulq_commerce.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bulq.bulq_commerce.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByAccount_id(Long id);
}

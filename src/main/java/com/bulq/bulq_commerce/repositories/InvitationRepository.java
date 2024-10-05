package com.bulq.bulq_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bulq.bulq_commerce.models.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    
}

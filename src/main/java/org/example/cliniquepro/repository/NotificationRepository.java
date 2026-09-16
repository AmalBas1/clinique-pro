package org.example.cliniquepro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.cliniquepro.entity.Notification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRendezVousId(Long rendezVousId);
}

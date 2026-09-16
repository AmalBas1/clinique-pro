package org.example.cliniquepro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.cliniquepro.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRendezVousId(Long rendezVousId);
}

package org.example.cliniquepro.repository;

import org.example.cliniquepro.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByNotification_RendezVousId(Long rendezVousId, Pageable pageable);
    long countByNotification_RendezVous_PatientId(Long patientId);
    long countByNotification_RendezVous_MedecinId(Long medecinId);

}

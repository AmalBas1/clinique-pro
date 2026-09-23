package org.example.cliniquepro.repository;

import org.example.cliniquepro.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByRendezVousId(Long rendezVousId, Pageable pageable);
    long countByRendezVous_PatientId(Long patientId);
    long countByRendezVous_MedecinId(Long medecinId);

}

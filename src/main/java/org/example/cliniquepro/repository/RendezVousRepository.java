package org.example.cliniquepro.repository;

import org.example.cliniquepro.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.cliniquepro.entity.RendezVous;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByDateRendezVousBetween(LocalDateTime debut, LocalDateTime fin);
    List<RendezVous> findByMedecinId(Long medecinId);
    Page<RendezVous> findByPatientId(Long patientId, Pageable pageable);
    Page<RendezVous> findByMedecinId(Long medecinId, Pageable pageable);
}

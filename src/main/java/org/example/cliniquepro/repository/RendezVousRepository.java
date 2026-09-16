package org.example.cliniquepro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.cliniquepro.entity.RendezVous;
import org.springframework.stereotype.Repository;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
}

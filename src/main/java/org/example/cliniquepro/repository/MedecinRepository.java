package org.example.cliniquepro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.cliniquepro.entity.Medecin;
import org.springframework.stereotype.Repository;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
}

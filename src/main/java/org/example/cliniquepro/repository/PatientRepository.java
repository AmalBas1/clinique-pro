package org.example.cliniquepro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.cliniquepro.entity.Patient;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}

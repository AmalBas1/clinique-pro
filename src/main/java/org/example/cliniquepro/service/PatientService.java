package org.example.cliniquepro.service;

import org.example.cliniquepro.dto.PatientDTO;
import org.springframework.data.domain.Page;

public interface PatientService {
    PatientDTO creerPatient(PatientDTO patientDTO);
    PatientDTO recupererPatientParId(Long id);
    Page<PatientDTO> recupererTousLesPatients(int page, int size, String sort);
    PatientDTO mettreAJourPatient(Long id, PatientDTO patientDTO);
    void supprimerPatient(Long id);
    PatientDTO recupererPatientParUserId(Long userId);
    PatientDTO updatePatientByUserId(Long userId, PatientDTO patientDTO);
}

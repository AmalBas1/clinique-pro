package org.example.cliniquepro.service;

import org.example.cliniquepro.dto.PatientDTO;
import java.util.List;

public interface PatientService {
    PatientDTO creerPatient(PatientDTO patientDTO);
    PatientDTO recupererPatientParId(Long id);
    List<PatientDTO> recupererTousLesPatients();
    PatientDTO mettreAJourPatient(Long id, PatientDTO patientDTO);
    void supprimerPatient(Long id);
}

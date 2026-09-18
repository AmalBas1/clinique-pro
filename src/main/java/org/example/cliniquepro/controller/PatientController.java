package org.example.cliniquepro.controller;

import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.PatientDTO;
import org.example.cliniquepro.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PatientDTO creerPatient(@RequestBody PatientDTO patientDTO) {
        return patientService.creerPatient(patientDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN', 'PATIENT')")
    public PatientDTO recupererPatientParId(@PathVariable Long id) {
        return patientService.recupererPatientParId(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN')")
    public Page<PatientDTO> recupererTousLesPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sort) {
        return patientService.recupererTousLesPatients(page, size, sort);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PATIENT')")
    public PatientDTO mettreAJourPatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        return patientService.mettreAJourPatient(id, patientDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void supprimerPatient(@PathVariable Long id) {
        patientService.supprimerPatient(id);
    }
}

package org.example.cliniquepro.controller;

import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.PatientDTO;
import org.example.cliniquepro.service.PatientService;
import org.springframework.data.domain.Page;
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
    public PatientDTO creerPatient(@RequestBody PatientDTO patientDTO) {
        return patientService.creerPatient(patientDTO);
    }

    @GetMapping("/{id}")
    public PatientDTO recupererPatientParId(@PathVariable Long id) {
        return patientService.recupererPatientParId(id);
    }

    @GetMapping
    public Page<PatientDTO> recupererTousLesPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sort) {
        return patientService.recupererTousLesPatients(page, size, sort);
    }

    @PutMapping("/{id}")
    public PatientDTO mettreAJourPatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        return patientService.mettreAJourPatient(id, patientDTO);
    }

    @DeleteMapping("/{id}")
    public void supprimerPatient(@PathVariable Long id) {
        patientService.supprimerPatient(id);
    }
}

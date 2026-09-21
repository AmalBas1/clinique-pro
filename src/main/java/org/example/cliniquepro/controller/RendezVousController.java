package org.example.cliniquepro.controller;

import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.RendezVousDTO;
import org.example.cliniquepro.enums.StatutRendezVous;
import org.example.cliniquepro.repository.RendezVousRepository;
import org.example.cliniquepro.service.RendezVousService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rendezvous")
public class RendezVousController {

    private final RendezVousService rendezVousService;
    private final RendezVousRepository rendezVousRepository;


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN', 'PATIENT')")
    public RendezVousDTO creerRendezVous(@RequestBody RendezVousDTO rendezVousDTO) {
        return rendezVousService.creerRendezVous(rendezVousDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN', 'PATIENT')")
    public RendezVousDTO recupererRendezVousParId(@PathVariable Long id) {
        return rendezVousService.recupererRendezVousParId(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN')")
    public Page<RendezVousDTO> recupererTousLesRendezVous(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sort) {
        return rendezVousService.recupererTousLesRendezVous(page, size, sort);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN')")
    public RendezVousDTO mettreAJourRendezVous(@PathVariable Long id, @RequestBody RendezVousDTO rendezVousDTO) {
        return rendezVousService.mettreAJourRendezVous(id, rendezVousDTO);
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN')")
    public RendezVousDTO changerStatutRendezVous(@PathVariable Long id,
                                                @RequestParam StatutRendezVous nouveauStatut) {
        return rendezVousService.changerStatutRendezVous(id, nouveauStatut);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void supprimerRendezVous(@PathVariable Long id) {
        rendezVousService.supprimerRendezVous(id);
    }

    @GetMapping("/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN')")
    public long countRendezVous() {
        return rendezVousRepository.count();
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN', 'PATIENT')")
    public Page<RendezVousDTO> recupererRendezVousParPatient(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return rendezVousService.recupererRendezVousParPatientId(patientId, page, size);
    }

    @GetMapping("/medecin/{medecinId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN')")
    public Page<RendezVousDTO> recupererRendezVousParMedecin(
            @PathVariable Long medecinId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return rendezVousService.recupererRendezVousParMedecinId(medecinId, page, size);
    }
}
package org.example.cliniquepro.controller;

import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.MedecinDTO;
import org.example.cliniquepro.service.MedecinService;
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
@RequestMapping("/api/medecins")
public class MedecinController {

    private final MedecinService medecinService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public MedecinDTO creerMedecin(@RequestBody MedecinDTO medecinDTO) {
        return medecinService.creerMedecin(medecinDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN', 'PATIENT')")
    public MedecinDTO recupererMedecinParId(@PathVariable Long id) {
        return medecinService.recupererMedecinParId(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN', 'PATIENT')")
    public Page<MedecinDTO> recupererTousLesMedecins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sort) {
        return medecinService.recupererTousLesMedecins(page, size, sort);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN')")
    public MedecinDTO mettreAJourMedecin(@PathVariable Long id, @RequestBody MedecinDTO medecinDTO) {
        return medecinService.mettreAJourMedecin(id, medecinDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void supprimerMedecin(@PathVariable Long id) {
        medecinService.supprimerMedecin(id);
    }

    @PostMapping("/{id}/indisponible")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN')")
    public MedecinDTO marquerIndisponible(@PathVariable Long id) {
        return medecinService.marquerIndisponible(id);
    }
}

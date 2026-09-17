package org.example.cliniquepro.controller;

import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.MedecinDTO;
import org.example.cliniquepro.service.MedecinService;
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
@RequestMapping("/api/medecins")
public class MedecinController {

    private final MedecinService medecinService;

    @PostMapping
    public MedecinDTO creerMedecin(@RequestBody MedecinDTO medecinDTO) {
        return medecinService.creerMedecin(medecinDTO);
    }

    @GetMapping("/{id}")
    public MedecinDTO recupererMedecinParId(@PathVariable Long id) {
        return medecinService.recupererMedecinParId(id);
    }

    @GetMapping
    public Page<MedecinDTO> recupererTousLesMedecins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sort) {
        return medecinService.recupererTousLesMedecins(page, size, sort);
    }

    @PutMapping("/{id}")
    public MedecinDTO mettreAJourMedecin(@PathVariable Long id, @RequestBody MedecinDTO medecinDTO) {
        return medecinService.mettreAJourMedecin(id, medecinDTO);
    }

    @DeleteMapping("/{id}")
    public void supprimerMedecin(@PathVariable Long id) {
        medecinService.supprimerMedecin(id);
    }
}

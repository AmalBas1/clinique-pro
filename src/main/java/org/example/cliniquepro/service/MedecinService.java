package org.example.cliniquepro.service;

import org.example.cliniquepro.dto.MedecinDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MedecinService {
    MedecinDTO creerMedecin(MedecinDTO medecinDTO);
    MedecinDTO recupererMedecinParId(Long id);
    Page<MedecinDTO> recupererTousLesMedecins(int page, int size, String sort);
    MedecinDTO mettreAJourMedecin(Long id, MedecinDTO medecinDTO);
    void supprimerMedecin(Long id);
    MedecinDTO marquerIndisponible(Long id);
    Page<MedecinDTO> recupererMedecinsDisponibles(int page, int size, String sort);
}

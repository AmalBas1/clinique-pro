package org.example.cliniquepro.service;

import org.example.cliniquepro.dto.MedecinDTO;
import java.util.List;

public interface MedecinService {
    MedecinDTO creerMedecin(MedecinDTO medecinDTO);
    MedecinDTO recupererMedecinParId(Long id);
    List<MedecinDTO> recupererTousLesMedecins();
    MedecinDTO mettreAJourMedecin(Long id, MedecinDTO medecinDTO);
    void supprimerMedecin(Long id);
}

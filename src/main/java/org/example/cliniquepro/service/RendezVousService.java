package org.example.cliniquepro.service;

import org.example.cliniquepro.dto.RendezVousDTO;
import org.example.cliniquepro.enums.StatutRendezVous;

import java.util.List;

public interface RendezVousService {
    RendezVousDTO creerRendezVous(RendezVousDTO rendezVousDTO);
    RendezVousDTO recupererRendezVousParId(Long id);
    List<RendezVousDTO> recupererTousLesRendezVous();
    RendezVousDTO mettreAJourRendezVous(Long id, RendezVousDTO rendezVousDTO);
    void supprimerRendezVous(Long id);
    RendezVousDTO changerStatutRendezVous(Long id, StatutRendezVous nouveauStatut);
}

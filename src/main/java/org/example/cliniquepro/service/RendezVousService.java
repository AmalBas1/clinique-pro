package org.example.cliniquepro.service;

import org.example.cliniquepro.dto.RendezVousDTO;
import org.example.cliniquepro.enums.StatutRendezVous;
import org.springframework.data.domain.Page;

public interface RendezVousService {
    RendezVousDTO creerRendezVous(RendezVousDTO rendezVousDTO);
    RendezVousDTO recupererRendezVousParId(Long id);
    Page<RendezVousDTO> recupererTousLesRendezVous(int page, int size, String sort);
    RendezVousDTO mettreAJourRendezVous(Long id, RendezVousDTO rendezVousDTO);
    void supprimerRendezVous(Long id);
    RendezVousDTO changerStatutRendezVous(Long id, StatutRendezVous nouveauStatut);
}

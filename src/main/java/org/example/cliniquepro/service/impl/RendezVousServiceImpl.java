package org.example.cliniquepro.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.RendezVousDTO;
import org.example.cliniquepro.entity.Medecin;
import org.example.cliniquepro.entity.Patient;
import org.example.cliniquepro.entity.RendezVous;
import org.example.cliniquepro.enums.StatutRendezVous;
import org.example.cliniquepro.mapper.RendezVousMapper;
import org.example.cliniquepro.repository.MedecinRepository;
import org.example.cliniquepro.repository.PatientRepository;
import org.example.cliniquepro.repository.RendezVousRepository;
import org.example.cliniquepro.service.RendezVousService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RendezVousServiceImpl implements RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final RendezVousMapper rendezVousMapper;

    @Override
    public RendezVousDTO creerRendezVous(RendezVousDTO rendezVousDTO) {
        RendezVous rendezVous = rendezVousMapper.toEntity(rendezVousDTO);

        if (rendezVousDTO.getPatientId() != null) {
            Patient patient = patientRepository.findById(rendezVousDTO.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient non trouvé avec l'ID : " + rendezVousDTO.getPatientId()));
            rendezVous.setPatient(patient);
        }

        if (rendezVousDTO.getMedecinId() != null) {
            Medecin medecin = medecinRepository.findById(rendezVousDTO.getMedecinId())
                    .orElseThrow(() -> new RuntimeException("Médecin non trouvé avec l'ID : " + rendezVousDTO.getMedecinId()));
            rendezVous.setMedecin(medecin);
        }

        RendezVous savedRendezVous = rendezVousRepository.save(rendezVous);
        return rendezVousMapper.toDTO(savedRendezVous);
    }

    @Override

    public RendezVousDTO recupererRendezVousParId(Long id) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec l'ID : " + id));
        return rendezVousMapper.toDTO(rendezVous);
    }

    @Override
    public Page<RendezVousDTO> recupererTousLesRendezVous(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sort), "id");
        Page<RendezVous> rendezVous = rendezVousRepository.findAll(pageable);
        return rendezVous.map(rendezVousMapper::toDTO);
    }

    @Override
    public RendezVousDTO mettreAJourRendezVous(Long id, RendezVousDTO rendezVousDTO) {
        RendezVous existingRendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec l'ID : " + id));

        rendezVousMapper.updateEntityFromDTO(rendezVousDTO, existingRendezVous);

        if (rendezVousDTO.getPatientId() != null) {
            Patient patient = patientRepository.findById(rendezVousDTO.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient non trouvé avec l'ID : " + rendezVousDTO.getPatientId()));
            existingRendezVous.setPatient(patient);
        }

        if (rendezVousDTO.getMedecinId() != null) {
            Medecin medecin = medecinRepository.findById(rendezVousDTO.getMedecinId())
                    .orElseThrow(() -> new RuntimeException("Médecin non trouvé avec l'ID : " + rendezVousDTO.getMedecinId()));
            existingRendezVous.setMedecin(medecin);
        }

        RendezVous updatedRendezVous = rendezVousRepository.save(existingRendezVous);
        return rendezVousMapper.toDTO(updatedRendezVous);
    }

    @Override
    public void supprimerRendezVous(Long id) {
        if (!rendezVousRepository.existsById(id)) {
            throw new RuntimeException("Rendez-vous non trouvé avec l'ID : " + id);
        }
        rendezVousRepository.deleteById(id);
    }
    @Override
    public RendezVousDTO changerStatutRendezVous(Long id, StatutRendezVous nouveauStatut) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec l'ID : " + id));

        rendezVous.setStatut(nouveauStatut);

        RendezVous updatedRendezVous = rendezVousRepository.save(rendezVous);
        return rendezVousMapper.toDTO(updatedRendezVous);
    }

    @Override
    public Page<RendezVousDTO> recupererRendezVousParPatientId(Long patientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateHeure").ascending());
        Page<RendezVous> rendezVousPage = rendezVousRepository.findByPatientId(patientId, pageable);
        return rendezVousPage.map(rendezVousMapper::toDTO);
    }

    @Override
    public Page<RendezVousDTO> recupererRendezVousParMedecinId(Long medecinId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateHeure").ascending());
        Page<RendezVous> rendezVousPage = rendezVousRepository.findByMedecinId(medecinId, pageable);
        return rendezVousPage.map(rendezVousMapper::toDTO);
    }
}

package org.example.cliniquepro.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.MedecinDTO;
import org.example.cliniquepro.dto.NotificationDTO;
import org.example.cliniquepro.entity.Medecin;
import org.example.cliniquepro.entity.RendezVous;
import org.example.cliniquepro.entity.User;
import org.example.cliniquepro.enums.TypeNotification;
import org.example.cliniquepro.mapper.MedecinMapper;
import org.example.cliniquepro.repository.MedecinRepository;
import org.example.cliniquepro.repository.RendezVousRepository;
import org.example.cliniquepro.repository.UserRepository;
import org.example.cliniquepro.service.MedecinService;
import org.example.cliniquepro.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MedecinServiceImpl implements MedecinService {

    private final MedecinRepository medecinRepository;
    private final UserRepository userRepository;
    private final RendezVousRepository rendezVousRepository;
    private final MedecinMapper medecinMapper;
    private final NotificationService notificationService;

    @Override
    public MedecinDTO creerMedecin(MedecinDTO medecinDTO) {
        Medecin medecin = medecinMapper.toEntity(medecinDTO);

        if (medecinDTO.getUserId() != null) {
            User user = userRepository.findById(medecinDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + medecinDTO.getUserId()));
            medecin.setUser(user);
        }

        Medecin savedMedecin = medecinRepository.save(medecin);
        return medecinMapper.toDTO(savedMedecin);
    }

    @Override
    public MedecinDTO recupererMedecinParId(Long id) {
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médecin non trouvé avec l'ID : " + id));
        return medecinMapper.toDTO(medecin);
    }

    @Override
    public Page<MedecinDTO> recupererTousLesMedecins(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sort), "id");
        Page<Medecin> medecins = medecinRepository.findAll(pageable);
        return medecins.map(medecinMapper::toDTO);
    }

    @Override
    public MedecinDTO mettreAJourMedecin(Long id, MedecinDTO medecinDTO) {
        Medecin existingMedecin = medecinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médecin non trouvé avec l'ID : " + id));

        medecinMapper.updateEntityFromDTO(medecinDTO, existingMedecin);

        if (medecinDTO.getUserId() != null) {
            User user = userRepository.findById(medecinDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + medecinDTO.getUserId()));
            existingMedecin.setUser(user);
        }

        Medecin updatedMedecin = medecinRepository.save(existingMedecin);
        return medecinMapper.toDTO(updatedMedecin);
    }

    @Override
    public void supprimerMedecin(Long id) {
        if (!medecinRepository.existsById(id)) {
            throw new RuntimeException("Médecin non trouvé avec l'ID : " + id);
        }
        medecinRepository.deleteById(id);
    }

    @Override
    public MedecinDTO marquerIndisponible(Long id) {
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médecin non trouvé avec l'ID : " + id));

        medecin.setDisponible(false);
        Medecin updatedMedecin = medecinRepository.save(medecin);

        List<RendezVous> rdvs = rendezVousRepository.findByMedecinId(id);

        for (RendezVous rdv : rdvs) {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setType(TypeNotification.MEDECIN_INDISPONIBLE);
            notificationDTO.setRendezVousId(rdv.getId());

            try {
                notificationService.creerNotification(notificationDTO);
            } catch (Exception e) {
                System.err.println("Erreur notification : " + e.getMessage());
            }
        }

        return medecinMapper.toDTO(updatedMedecin);
    }
}



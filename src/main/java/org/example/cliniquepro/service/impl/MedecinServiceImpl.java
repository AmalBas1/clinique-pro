package org.example.cliniquepro.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.entity.Medecin;
import org.example.cliniquepro.entity.User;
import org.example.cliniquepro.mapper.MedecinMapper;
import org.example.cliniquepro.repository.MedecinRepository;
import org.example.cliniquepro.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collections;

import org.example.cliniquepro.service.MedecinService;
import org.example.cliniquepro.dto.MedecinDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class MedecinServiceImpl implements MedecinService {

    private final MedecinRepository medecinRepository;
    private final UserRepository userRepository;
    private final MedecinMapper medecinMapper;

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
    public List<MedecinDTO> recupererTousLesMedecins() {
        List<Medecin> medecins = medecinRepository.findAll();
        return medecinMapper.toDtoList(medecins);
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
}

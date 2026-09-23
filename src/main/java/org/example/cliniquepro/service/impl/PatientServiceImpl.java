package org.example.cliniquepro.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.PatientDTO;
import org.example.cliniquepro.entity.Patient;
import org.example.cliniquepro.entity.User;
import org.example.cliniquepro.mapper.PatientMapper;
import org.example.cliniquepro.repository.PatientRepository;
import org.example.cliniquepro.repository.UserRepository;
import org.example.cliniquepro.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final PatientMapper patientMapper;

    @Override
    public PatientDTO creerPatient(PatientDTO patientDTO) {
        Patient patient = patientMapper.toEntity(patientDTO);

        if (patientDTO.getUserId() != null) {
            User user = userRepository.findById(patientDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + patientDTO.getUserId()));
            patient.setUser(user);
        }


        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toDTO(savedPatient);
    }

    @Override
    public PatientDTO recupererPatientParId(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé avec l'ID : " + id));
        return patientMapper.toDTO(patient);
    }

    @Override
    public Page<PatientDTO> recupererTousLesPatients(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sort), "id");
        Page<Patient> patients = patientRepository.findAll(pageable);
        return patients.map(patientMapper::toDTO);
    }

    @Override
    public PatientDTO mettreAJourPatient(Long id, PatientDTO patientDTO) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé avec l'ID : " + id));

        patientMapper.updateEntityFromDTO(patientDTO, existingPatient);

        if (patientDTO.getUserId() != null) {
            User user = userRepository.findById(patientDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + patientDTO.getUserId()));
            existingPatient.setUser(user);
        }


        Patient updatedPatient = patientRepository.save(existingPatient);
        return patientMapper.toDTO(updatedPatient);
    }

    @Override
    public void supprimerPatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient non trouvé avec l'ID : " + id);
        }
        patientRepository.deleteById(id);
    }
    @Override
    public PatientDTO recupererPatientParUserId(Long userId) {
        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé pour cet utilisateur avec l'ID: " + userId));

        return recupererPatientParId(patient.getId());
    }

    @Override
    public PatientDTO updatePatientByUserId(Long userId, PatientDTO patientDTO) {
        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profil patient non trouvé pour cet utilisateur ID: " + userId));

        patient.setTelephone(patientDTO.getTelephone());
        patient.setAdresse(patientDTO.getAdresse());

        Patient updatedPatient = patientRepository.save(patient);
        return patientMapper.toDTO(updatedPatient);
    }
}
package org.example.cliniquepro.service.impl;

import org.example.cliniquepro.dto.PatientDTO;
import org.example.cliniquepro.entity.Patient;
import org.example.cliniquepro.entity.User;
import org.example.cliniquepro.enums.Role;
import org.example.cliniquepro.repository.MedecinRepository;
import org.example.cliniquepro.repository.PatientRepository;
import org.example.cliniquepro.repository.UserRepository;
import org.example.cliniquepro.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class PatientServiceImplTest {

    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Test
    void creerPatient() {
        User user = new User();
        user.setEmail("patient1@test.com");
        user.setPassword("1234");
        user.setNom("Durand");
        user.setPrenom("Paul");
        user.setRole(Role.ADMIN);
        userRepository.save(user);



        PatientDTO dto = new PatientDTO();
        dto.setNom("Durand");
        dto.setPrenom("Paul");
        dto.setTelephone("0700000000");
        dto.setAdresse("Paris");
        dto.setDateNaissance(LocalDate.of(1990, 1, 5));
        dto.setUserId(user.getId());

        PatientDTO resultat = patientService.creerPatient(dto);

        assertNotNull(resultat.getId());
        assertEquals("Durand", resultat.getNom());
    }

    @Test
    void recupererPatientParId() {
        User user = new User();
        user.setEmail("patient2@test.com");
        user.setPassword("1234");
        user.setNom("Bernard");
        user.setPrenom("Claire");
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        Patient patient = new Patient(null, "0711111111", "Lyon", LocalDate.of(1988, 6, 12), user);
        patientRepository.save(patient);

        PatientDTO resultat = patientService.recupererPatientParId(patient.getId());

        assertEquals("Bernard", resultat.getNom());
    }

    @Test
    void modifierPatient() {
        User user = new User();
        user.setEmail("patient3@test.com");
        user.setPassword("1234");
        user.setNom("Morel");
        user.setPrenom("Luc");
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        Patient patient = new Patient(null, "0722222222", "Marseille", LocalDate.of(1978, 3, 18), user);
        patientRepository.save(patient);

        PatientDTO dto = new PatientDTO();
        dto.setNom("NouveauNom");
        dto.setPrenom("Luc");
        dto.setTelephone("0722222222");
        dto.setAdresse("Marseille");
        dto.setDateNaissance(LocalDate.of(1978, 3, 18));

        PatientDTO resultat = patientService.mettreAJourPatient(patient.getId(), dto);

        assertEquals("NouveauNom", resultat.getNom());
        patientRepository.flush();
        assertEquals("NouveauNom", userRepository.findById(user.getId()).orElseThrow().getNom());
    }

    @Test
    void lireIdentiteDepuisUtilisateur() {
        User user = new User();
        user.setEmail("identite-patient@test.com");
        user.setNom("Initial");
        user.setPrenom("Avant");
        user.setRole(Role.PATIENT);
        userRepository.save(user);

        Patient profile = new Patient();
        profile.setUser(user);
        patientRepository.save(profile);

        user.setNom("ModifieDansUser");
        user.setPrenom("Apres");
        userRepository.saveAndFlush(user);

        PatientDTO dto = patientService.recupererPatientParId(profile.getId());
        assertEquals("ModifieDansUser", dto.getNom());
        assertEquals("Apres", dto.getPrenom());
        assertEquals(user.getId(), dto.getUserId());
    }

    @Autowired
    private jakarta.persistence.EntityManager entityManager;

    @Test
    void supprimerProfilLieUtilisateur() {
        User user = new User();
        user.setEmail("suppression-patient@test.com");
        user.setRole(Role.PATIENT);
        userRepository.save(user);
        Patient profile = new Patient();
        profile.setUser(user);
        user.setPatient(profile);
        patientRepository.saveAndFlush(profile);
        Long profileId = profile.getId();
        Long userId = user.getId();
        entityManager.clear();

        patientService.supprimerPatient(profileId);
        patientRepository.flush();
        entityManager.clear();

        org.junit.jupiter.api.Assertions.assertFalse(patientRepository.existsById(profileId));
        org.junit.jupiter.api.Assertions.assertTrue(userRepository.existsById(userId));
    }
}

package org.example.cliniquepro.service.impl;

import org.example.cliniquepro.dto.PatientDTO;
import org.example.cliniquepro.entity.Medecin;
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

        Medecin medecin = new Medecin(null, "Martin", "Alice", "0600000000", "Cardiologie", user);
        medecinRepository.save(medecin);

        PatientDTO dto = new PatientDTO();
        dto.setNom("Durand");
        dto.setPrenom("Paul");
        dto.setTelephone("0700000000");
        dto.setAdresse("Paris");
        dto.setDateNaissance(LocalDate.of(1990, 1, 5));
        dto.setUserId(user.getId());
        dto.setMedecinId(medecin.getId());

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

        Patient patient = new Patient(null, "Bernard", "Claire", "0711111111", "Lyon", LocalDate.of(1988, 6, 12), user, null);
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

        Patient patient = new Patient(null, "Morel", "Luc", "0722222222", "Marseille", LocalDate.of(1978, 3, 18), user, null);
        patientRepository.save(patient);

        PatientDTO dto = new PatientDTO();
        dto.setNom("NouveauNom");
        dto.setPrenom("Luc");
        dto.setTelephone("0722222222");
        dto.setAdresse("Marseille");
        dto.setDateNaissance(LocalDate.of(1978, 3, 18));

        PatientDTO resultat = patientService.mettreAJourPatient(patient.getId(), dto);

        assertEquals("NouveauNom", resultat.getNom());
    }
}

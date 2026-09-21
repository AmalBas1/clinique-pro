package org.example.cliniquepro.service.impl;

import org.example.cliniquepro.dto.MessageDTO;
import org.example.cliniquepro.entity.Medecin;
import org.example.cliniquepro.entity.Message;
import org.example.cliniquepro.entity.Patient;
import org.example.cliniquepro.entity.RendezVous;
import org.example.cliniquepro.entity.User;
import org.example.cliniquepro.enums.Role;
import org.example.cliniquepro.enums.StatutRendezVous;
import org.example.cliniquepro.repository.MedecinRepository;
import org.example.cliniquepro.repository.MessageRepository;
import org.example.cliniquepro.repository.PatientRepository;
import org.example.cliniquepro.repository.RendezVousRepository;
import org.example.cliniquepro.repository.UserRepository;
import org.example.cliniquepro.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class MessageServiceImplTest {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Test
    void creerMessage() {
        User patientUser = new User();
        patientUser.setEmail("patient-msg@test.com");
        patientUser.setPassword("1234");
        patientUser.setNom("Patient");
        patientUser.setPrenom("Test");
        patientUser.setRole(Role.ADMIN);
        userRepository.save(patientUser);

        User medecinUser = new User();
        medecinUser.setEmail("medecin-msg@test.com");
        medecinUser.setPassword("1234");
        medecinUser.setNom("Medecin");
        medecinUser.setPrenom("Test");
        medecinUser.setRole(Role.MEDECIN);
        userRepository.save(medecinUser);

        Patient patient = new Patient(null, "Patient", "Test", "0700000000", "Paris", LocalDate.of(1991, 1, 1), patientUser);
        patientRepository.save(patient);

        Medecin medecin = new Medecin(null, "Medecin", "Test", "0600000000", "Generaliste", medecinUser,true);
        medecinRepository.save(medecin);

        RendezVous rendezVous = new RendezVous(null, LocalDateTime.of(2026, 1, 15, 9, 0), StatutRendezVous.PENDING, patient, medecin);
        rendezVousRepository.save(rendezVous);

        MessageDTO dto = new MessageDTO();
        dto.setContenu("Bonjour, votre consultation est confirmée.");
        dto.setExpediteurRole("MEDECIN");
        dto.setRendezVousId(rendezVous.getId());

        MessageDTO resultat = messageService.creerMessage(dto);

        assertNotNull(resultat.getId());
        assertEquals("Bonjour, votre consultation est confirmée.", resultat.getContenu());
    }

    @Test
    void recupererMessageParId() {
        User patientUser = new User();
        patientUser.setEmail("patient-msg2@test.com");
        patientUser.setPassword("1234");
        patientUser.setNom("Patient2");
        patientUser.setPrenom("Test");
        patientUser.setRole(Role.ADMIN);
        userRepository.save(patientUser);

        User medecinUser = new User();
        medecinUser.setEmail("medecin-msg2@test.com");
        medecinUser.setPassword("1234");
        medecinUser.setNom("Medecin2");
        medecinUser.setPrenom("Test");
        medecinUser.setRole(Role.MEDECIN);
        userRepository.save(medecinUser);

        Patient patient = new Patient(null, "Patient2", "Test", "0711111111", "Lyon", LocalDate.of(1987, 4, 4), patientUser);
        patientRepository.save(patient);

        Medecin medecin = new Medecin(null, "Medecin2", "Test", "0611111111", "Cardiologie", medecinUser,true);
        medecinRepository.save(medecin);

        RendezVous rendezVous = new RendezVous(null, LocalDateTime.of(2026, 2, 20, 10, 30), StatutRendezVous.CONFIRMED, patient, medecin);
        rendezVousRepository.save(rendezVous);

        Message message = new Message(null, "Merci pour votre message", LocalDateTime.now(), "MEDECIN", rendezVous);
        messageRepository.save(message);

        MessageDTO resultat = messageService.recupererMessageParId(message.getId());

        assertEquals("Merci pour votre message", resultat.getContenu());
    }

    @Test
    void recupererMessagesParRendezVous() {
        User patientUser = new User();
        patientUser.setEmail("patient-msg3@test.com");
        patientUser.setPassword("1234");
        patientUser.setNom("Patient3");
        patientUser.setPrenom("Test");
        patientUser.setRole(Role.ADMIN);
        userRepository.save(patientUser);

        User medecinUser = new User();
        medecinUser.setEmail("medecin-msg3@test.com");
        medecinUser.setPassword("1234");
        medecinUser.setNom("Medecin3");
        medecinUser.setPrenom("Test");
        medecinUser.setRole(Role.MEDECIN);
        userRepository.save(medecinUser);

        Patient patient = new Patient(null, "Patient3", "Test", "0722222222", "Nice", LocalDate.of(1985, 9, 9), patientUser);
        patientRepository.save(patient);

        Medecin medecin = new Medecin(null, "Medecin3", "Test", "0622222222", "Generaliste", medecinUser,true);
        medecinRepository.save(medecin);

        RendezVous rendezVous = new RendezVous(null, LocalDateTime.of(2026, 3, 5, 11, 15), StatutRendezVous.CONFIRMED, patient, medecin);
        rendezVousRepository.save(rendezVous);

        Message message1 = new Message(null, "Premier message", LocalDateTime.now(), "MEDECIN", rendezVous);
        Message message2 = new Message(null, "Deuxième message", LocalDateTime.now(), "PATIENT", rendezVous);
        messageRepository.save(message1);
        messageRepository.save(message2);

        Page<MessageDTO> resultat = messageService.recupererMessagesParRendezVous(rendezVous.getId(), 0, 10, "ASC");

        assertEquals(2, resultat.getTotalElements());
        assertEquals("Premier message", resultat.getContent().get(0).getContenu());
    }
}

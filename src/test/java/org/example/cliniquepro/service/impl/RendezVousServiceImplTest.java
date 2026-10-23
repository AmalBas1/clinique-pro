package org.example.cliniquepro.service.impl;

import org.example.cliniquepro.dto.RendezVousDTO;
import org.example.cliniquepro.entity.Medecin;
import org.example.cliniquepro.entity.Message;
import org.example.cliniquepro.entity.Notification;
import org.example.cliniquepro.repository.MessageRepository;
import org.example.cliniquepro.repository.NotificationRepository;
import org.example.cliniquepro.entity.Patient;
import org.example.cliniquepro.entity.RendezVous;
import org.example.cliniquepro.entity.User;
import org.example.cliniquepro.enums.Role;
import org.example.cliniquepro.enums.StatutRendezVous;
import org.example.cliniquepro.repository.MedecinRepository;
import org.example.cliniquepro.repository.PatientRepository;
import org.example.cliniquepro.repository.RendezVousRepository;
import org.example.cliniquepro.repository.UserRepository;
import org.example.cliniquepro.service.RendezVousService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class RendezVousServiceImplTest {

    @Autowired
    private RendezVousService rendezVousService;
    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private jakarta.persistence.EntityManager entityManager;

    @Test
    void creerRendezVous() {
        User userPatient = new User();
        userPatient.setEmail("patient-rdv@test.com");
        userPatient.setPassword("1234");
        userPatient.setNom("Patient");
        userPatient.setPrenom("Test");
        userPatient.setRole(Role.ADMIN);
        userRepository.save(userPatient);

        User userMedecin = new User();
        userMedecin.setEmail("medecin-rdv@test.com");
        userMedecin.setPassword("1234");
        userMedecin.setNom("Medecin");
        userMedecin.setPrenom("Test");
        userMedecin.setRole(Role.MEDECIN);
        userRepository.save(userMedecin);

        Patient patient = new Patient(null, "0700000000", "Paris", LocalDate.of(1992, 5, 20), userPatient);
        patientRepository.save(patient);

        Medecin medecin = new Medecin(null, "0600000000", "Cardiologie", userMedecin,true);
        medecinRepository.save(medecin);

        RendezVousDTO dto = new RendezVousDTO();
        dto.setDateRendezVous(LocalDateTime.of(2026, 10, 10, 9, 30));
        dto.setStatut(StatutRendezVous.PENDING);
        dto.setPatientId(patient.getId());
        dto.setMedecinId(medecin.getId());

        RendezVousDTO resultat = rendezVousService.creerRendezVous(dto);

        assertNotNull(resultat.getId());
        assertEquals(StatutRendezVous.PENDING, resultat.getStatut());
    }

    @Test
    void recupererRendezVousParId() {
        User userPatient = new User();
        userPatient.setEmail("patient-rdv2@test.com");
        userPatient.setPassword("1234");
        userPatient.setNom("Patient2");
        userPatient.setPrenom("Test");
        userPatient.setRole(Role.ADMIN);
        userRepository.save(userPatient);

        User userMedecin = new User();
        userMedecin.setEmail("medecin-rdv2@test.com");
        userMedecin.setPassword("1234");
        userMedecin.setNom("Medecin2");
        userMedecin.setPrenom("Test");
        userMedecin.setRole(Role.MEDECIN);
        userRepository.save(userMedecin);

        Patient patient = new Patient(null, "0711111111", "Lyon", LocalDate.of(1989, 2, 15), userPatient);
        patientRepository.save(patient);

        Medecin medecin = new Medecin(null, "0611111111", "Cardiologie", userMedecin,true);
        medecinRepository.save(medecin);

        RendezVous rendezVous = new RendezVous(null, LocalDateTime.of(2026, 11, 12, 14, 0), StatutRendezVous.CONFIRMED, patient, medecin);
        rendezVousRepository.save(rendezVous);

        RendezVousDTO resultat = rendezVousService.recupererRendezVousParId(rendezVous.getId());

        assertEquals(StatutRendezVous.CONFIRMED, resultat.getStatut());
    }

    @Test
    void changerStatutRendezVous() {
        User userPatient = new User();
        userPatient.setEmail("patient-rdv3@test.com");
        userPatient.setPassword("1234");
        userPatient.setNom("Patient3");
        userPatient.setPrenom("Test");
        userPatient.setRole(Role.ADMIN);
        userRepository.save(userPatient);

        User userMedecin = new User();
        userMedecin.setEmail("medecin-rdv3@test.com");
        userMedecin.setPassword("1234");
        userMedecin.setNom("Medecin3");
        userMedecin.setPrenom("Test");
        userMedecin.setRole(Role.MEDECIN);
        userRepository.save(userMedecin);

        Patient patient = new Patient(null, "0722222222", "Nice", LocalDate.of(1985, 7, 3), userPatient);
        patientRepository.save(patient);

        Medecin medecin = new Medecin(null, "0622222222", "Generaliste", userMedecin,true);
        medecinRepository.save(medecin);

        RendezVous rendezVous = new RendezVous(null, LocalDateTime.of(2026, 12, 1, 10, 0), StatutRendezVous.PENDING, patient, medecin);
        rendezVousRepository.save(rendezVous);

        RendezVousDTO resultat = rendezVousService.changerStatutRendezVous(rendezVous.getId(), StatutRendezVous.CANCELLED);

        assertEquals(StatutRendezVous.CANCELLED, resultat.getStatut());
    }

    @Test
    void supprimerRendezVousAvecDiscussion() {
        RendezVous rdv = rendezVousRepository.save(new RendezVous());
        Notification notification = new Notification();
        notification.setRendezVous(rdv);
        notificationRepository.save(notification);
        Message message = new Message();
        message.setContenu("Discussion a supprimer");
        message.setNotification(notification);
        messageRepository.save(message);

        RendezVous autreRdv = rendezVousRepository.save(new RendezVous());
        Notification autreNotification = new Notification();
        autreNotification.setRendezVous(autreRdv);
        notificationRepository.save(autreNotification);
        Message autreMessage = new Message();
        autreMessage.setNotification(autreNotification);
        messageRepository.saveAndFlush(autreMessage);
        entityManager.clear();

        rendezVousService.supprimerRendezVous(rdv.getId());
        rendezVousRepository.flush();
        entityManager.clear();

        assertFalse(rendezVousRepository.existsById(rdv.getId()));
        assertFalse(notificationRepository.existsById(notification.getId()));
        assertFalse(messageRepository.existsById(message.getId()));
        assertTrue(rendezVousRepository.existsById(autreRdv.getId()));
        assertTrue(notificationRepository.existsById(autreNotification.getId()));
        assertTrue(messageRepository.existsById(autreMessage.getId()));
    }

    @Test
    void supprimerRendezVousSansDiscussion() {
        RendezVous rdv = rendezVousRepository.saveAndFlush(new RendezVous());
        entityManager.clear();

        rendezVousService.supprimerRendezVous(rdv.getId());
        rendezVousRepository.flush();
        entityManager.clear();

        assertFalse(rendezVousRepository.existsById(rdv.getId()));
    }
}

package org.example.cliniquepro.service.impl;

import org.example.cliniquepro.dto.NotificationDTO;
import org.example.cliniquepro.entity.Medecin;
import org.example.cliniquepro.entity.Notification;
import org.example.cliniquepro.entity.Patient;
import org.example.cliniquepro.entity.RendezVous;
import org.example.cliniquepro.entity.User;
import org.example.cliniquepro.enums.Role;
import org.example.cliniquepro.enums.StatutRendezVous;
import org.example.cliniquepro.enums.TypeNotification;
import org.example.cliniquepro.repository.MedecinRepository;
import org.example.cliniquepro.repository.NotificationRepository;
import org.example.cliniquepro.repository.PatientRepository;
import org.example.cliniquepro.repository.RendezVousRepository;
import org.example.cliniquepro.repository.UserRepository;
import org.example.cliniquepro.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class NotificationServiceImplTest {

    @Autowired    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Test
    void recupererNotificationParId() {
        User patientUser = new User();
        patientUser.setEmail("patient-notif2@test.com");
        patientUser.setPassword("1234");
        patientUser.setNom("Patient2");
        patientUser.setPrenom("Test");
        patientUser.setRole(Role.ADMIN);
        userRepository.save(patientUser);

        User medecinUser = new User();
        medecinUser.setEmail("medecin-notif2@test.com");
        medecinUser.setPassword("1234");
        medecinUser.setNom("Medecin2");
        medecinUser.setPrenom("Test");
        medecinUser.setRole(Role.MEDECIN);
        userRepository.save(medecinUser);

        Patient patient = new Patient(null, "Patient2", "Test", "0711111111", "Lyon", LocalDate.of(1993, 8, 10), patientUser, null, new ArrayList<>());
        patientRepository.save(patient);

        Medecin medecin = new Medecin(null, "Medecin2", "Test", "0611111111", "Cardiologie", medecinUser, new ArrayList<>());
        medecinRepository.save(medecin);

        RendezVous rendezVous = new RendezVous(null, LocalDateTime.of(2026, 5, 12, 8, 30), StatutRendezVous.CONFIRMED, patient, medecin, new ArrayList<>(), new ArrayList<>());
        rendezVousRepository.save(rendezVous);

        Notification notification = new Notification(null, "Notification importante", LocalDateTime.now(), TypeNotification.NOUVEAU_MESSAGE, rendezVous);
        notificationRepository.save(notification);

        NotificationDTO resultat = notificationService.recupererNotificationParId(notification.getId());

        assertEquals("Notification importante", resultat.getContenu());
    }

    @Test
    void recupererNotificationsParRendezVous() {
        User patientUser = new User();
        patientUser.setEmail("patient-notif3@test.com");
        patientUser.setPassword("1234");
        patientUser.setNom("Patient3");
        patientUser.setPrenom("Test");
        patientUser.setRole(Role.ADMIN);
        userRepository.save(patientUser);

        User medecinUser = new User();
        medecinUser.setEmail("medecin-notif3@test.com");
        medecinUser.setPassword("1234");
        medecinUser.setNom("Medecin3");
        medecinUser.setPrenom("Test");
        medecinUser.setRole(Role.MEDECIN);
        userRepository.save(medecinUser);

        Patient patient = new Patient(null, "Patient3", "Test", "0722222222", "Nice", LocalDate.of(1988, 11, 25), patientUser, null, new ArrayList<>());
        patientRepository.save(patient);

        Medecin medecin = new Medecin(null, "Medecin3", "Test", "0622222222", "Generaliste", medecinUser, new ArrayList<>());
        medecinRepository.save(medecin);

        RendezVous rendezVous = new RendezVous(null, LocalDateTime.of(2026, 6, 18, 15, 45), StatutRendezVous.CONFIRMED, patient, medecin, new ArrayList<>(), new ArrayList<>());
        rendezVousRepository.save(rendezVous);

        Notification notification1 = new Notification(null, "Première notification", LocalDateTime.now(), TypeNotification.RAPPEL_RDV, rendezVous);
        Notification notification2 = new Notification(null, "Deuxième notification", LocalDateTime.now(), TypeNotification.NOUVEAU_MESSAGE, rendezVous);
        notificationRepository.save(notification1);
        notificationRepository.save(notification2);

        Page<NotificationDTO> resultat = notificationService.recupererNotificationsParRendezVous(rendezVous.getId(), 0, 10, "ASC");

        assertEquals(2, resultat.getTotalElements());
        assertEquals("Première notification", resultat.getContent().get(0).getContenu());
    }

    @Test
    void supprimerNotification() {
        User patientUser = new User();
        patientUser.setEmail("patient-notif4@test.com");
        patientUser.setPassword("1234");
        patientUser.setNom("Patient4");
        patientUser.setPrenom("Test");
        patientUser.setRole(Role.ADMIN);
        userRepository.save(patientUser);

        User medecinUser = new User();
        medecinUser.setEmail("medecin-notif4@test.com");
        medecinUser.setPassword("1234");
        medecinUser.setNom("Medecin4");
        medecinUser.setPrenom("Test");
        medecinUser.setRole(Role.MEDECIN);
        userRepository.save(medecinUser);

        Patient patient = new Patient(null, "Patient4", "Test", "0733333333", "Bordeaux", LocalDate.of(1991, 5, 7), patientUser, null, new ArrayList<>());
        patientRepository.save(patient);

        Medecin medecin = new Medecin(null, "Medecin4", "Test", "0633333333", "Generaliste", medecinUser, new ArrayList<>());
        medecinRepository.save(medecin);

        RendezVous rendezVous = new RendezVous(null, LocalDateTime.of(2026, 7, 3, 13, 15), StatutRendezVous.PENDING, patient, medecin, new ArrayList<>(), new ArrayList<>());
        rendezVousRepository.save(rendezVous);

        Notification notification = new Notification(null, "À supprimer", LocalDateTime.now(), TypeNotification.RAPPEL_RDV, rendezVous);
        Notification saved = notificationRepository.save(notification);

        notificationService.supprimerNotification(saved.getId());

        assertFalse(notificationRepository.existsById(saved.getId()));
    }
}

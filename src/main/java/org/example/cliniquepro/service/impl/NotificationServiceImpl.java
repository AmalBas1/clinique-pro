package org.example.cliniquepro.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.NotificationDTO;
import org.example.cliniquepro.entity.Notification;
import org.example.cliniquepro.entity.RendezVous;
import org.example.cliniquepro.enums.TypeNotification;
import org.example.cliniquepro.mapper.NotificationMapper;
import org.example.cliniquepro.repository.NotificationRepository;
import org.example.cliniquepro.repository.RendezVousRepository;
import org.example.cliniquepro.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final RendezVousRepository rendezVousRepository;
    private final NotificationMapper notificationMapper;
    private final JavaMailSender mailSender;



    private  String genererContenuParDefaut(TypeNotification type, RendezVous rendezVous) {
        if (type == null) {
            return "Vous avez une nouvelle notification concernant votre rendez-vous.";
        }

        switch (type) {
            case ANNULATION_RDV:
                return "Nous vous informons que votre rendez-vous prévu le "
                        + rendezVous.getDateRendezVous() + " a été annulé.";

            case MEDECIN_INDISPONIBLE:
                return "Votre médecin est exceptionnellement indisponible pour le rendez-vous prévu le "
                        + rendezVous.getDateRendezVous() + ". Nous vous invitons à en reprogrammer un.";


            case NOUVEAU_MESSAGE:
                return "Vous avez reçu un nouveau message de la clinique concernant votre rendez-vous.";

            default:
                return "Vous avez une mise à jour concernant votre rendez-vous.";
        }
    }

    @Override
    public NotificationDTO creerNotification(NotificationDTO notificationDTO) {

        if (notificationDTO.getRendezVousId() != null) {
            RendezVous rendezVous = rendezVousRepository.findById(notificationDTO.getRendezVousId())
                    .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec l'ID : " + notificationDTO.getRendezVousId()));

            if (notificationDTO.getContenu() == null || notificationDTO.getContenu().trim().isEmpty()) {
                String contenuGenere = genererContenuParDefaut(notificationDTO.getType(), rendezVous);
                notificationDTO.setContenu(contenuGenere);
            }

            Notification notification = notificationMapper.toEntity(notificationDTO);
            notification.setRendezVous(rendezVous);

            if (notification.getDateCreation() == null) {
                notification.setDateCreation(LocalDateTime.now());
            }

            Notification savedNotification = notificationRepository.save(notification);

            envoyerEmailNotification(rendezVous, notificationDTO.getType(), notificationDTO.getContenu());

            return notificationMapper.toDTO(savedNotification);

        } else {
            throw new RuntimeException("Une notification doit obligatoirement être liée à un rendez-vous.");
        }
    }



    private void envoyerEmailNotification(RendezVous rendezVous, TypeNotification typeNotification, String contenuExistant) {
        try {
            String emailDestinataire = rendezVous.getPatient().getUser().getEmail();
            String sujet = "CliniquePro - Notification";

            if (typeNotification != null) {
                switch (typeNotification) {
                    case ANNULATION_RDV:
                        sujet = "CliniquePro - Annulation de votre rendez-vous";
                        break;
                    case RAPPEL_RDV:
                        sujet = "CliniquePro - Rappel de votre rendez-vous";
                        break;
                    case MEDECIN_INDISPONIBLE:
                        sujet = "CliniquePro - Indisponibilité de votre médecin";
                        break;
                    case NOUVEAU_MESSAGE:
                        sujet = "CliniquePro - Nouveau message concernant votre rendez-vous";
                        break;
                }
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@cliniquepro.com");
            message.setTo(emailDestinataire);
            message.setSubject(sujet);
            message.setText(contenuExistant);

            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'e-mail de notification : " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationDTO recupererNotificationParId(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification non trouvée avec l'ID : " + id));
        return notificationMapper.toDTO(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationDTO> recupererNotificationsParRendezVous(Long rendezVousId, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sort), "id");
        Page<Notification> notifications = notificationRepository.findByRendezVousId(rendezVousId, pageable);
        return notifications.map(notificationMapper::toDTO);
    }

    @Override
    public void supprimerNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification non trouvée avec l'ID : " + id);
        }
        notificationRepository.deleteById(id);
    }
}
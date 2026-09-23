package org.example.cliniquepro.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.NotificationDTO;
import org.example.cliniquepro.entity.RendezVous;
import org.example.cliniquepro.enums.TypeNotification;
import org.example.cliniquepro.repository.RendezVousRepository;
import org.example.cliniquepro.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RendezVousScheduler {

    private final RendezVousRepository rendezVousRepository;
    private final NotificationService notificationService;

    @Scheduled(fixedRate = 60000)
    public void verifierEtEnvoyerRappels() {
        LocalDateTime maintenant = LocalDateTime.now();

        LocalDateTime debut24h = maintenant.plusHours(24);
        LocalDateTime fin24h = maintenant.plusHours(24).plusMinutes(1);

        List<RendezVous> rdvPourRappel24h = rendezVousRepository.findByDateRendezVousBetween(debut24h, fin24h);
        for (RendezVous rdv : rdvPourRappel24h) {
            envoyerNotificationRappel(rdv, "Rappel : Vous avez un rendez-vous demain à " + rdv.getDateRendezVous().toLocalTime());
        }

        LocalDateTime debut1h = maintenant.plusHours(1);
        LocalDateTime fin1h = maintenant.plusHours(1).plusMinutes(1);

        List<RendezVous> rdvPourRappel1h = rendezVousRepository.findByDateRendezVousBetween(debut1h, fin1h);
        for (RendezVous rdv : rdvPourRappel1h) {
            envoyerNotificationRappel(rdv, "Rappel urgent : Votre rendez-vous commence dans 1 heure !");
        }
    }

    private void envoyerNotificationRappel(RendezVous rdv, String contenu) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setType(TypeNotification.RAPPEL_RDV);
        notificationDTO.setContenu(contenu);
        notificationDTO.setRendezVousId(rdv.getId());
        notificationDTO.setDateCreation(LocalDateTime.now());

        notificationService.creerNotification(notificationDTO);
    }
}
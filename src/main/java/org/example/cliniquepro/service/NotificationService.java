package org.example.cliniquepro.service;

import org.example.cliniquepro.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {
    NotificationDTO creerNotification(NotificationDTO notificationDTO);
    NotificationDTO recupererNotificationParId(Long id);
    List<NotificationDTO> recupererNotificationsParRendezVous(Long rendezVousId);
    void supprimerNotification(Long id);
}

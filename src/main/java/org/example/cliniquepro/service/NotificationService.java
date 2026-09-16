package org.example.cliniquepro.service;

import org.example.cliniquepro.dto.NotificationDTO;
import org.springframework.data.domain.Page;

public interface NotificationService {
    NotificationDTO creerNotification(NotificationDTO notificationDTO);
    NotificationDTO recupererNotificationParId(Long id);
    Page<NotificationDTO> recupererNotificationsParRendezVous(Long rendezVousId, int page, int size, String sort);
    void supprimerNotification(Long id);
}

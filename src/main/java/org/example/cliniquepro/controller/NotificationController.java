package org.example.cliniquepro.controller;

import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.NotificationDTO;
import org.example.cliniquepro.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public NotificationDTO creerNotification(@RequestBody NotificationDTO notificationDTO) {
        return notificationService.creerNotification(notificationDTO);
    }

    @GetMapping("/{id}")
    public NotificationDTO recupererNotificationParId(@PathVariable Long id) {
        return notificationService.recupererNotificationParId(id);
    }

    @GetMapping("/rendez-vous/{rendezVousId}")
    public Page<NotificationDTO> recupererNotificationsParRendezVous(
            @PathVariable Long rendezVousId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sort) {
        return notificationService.recupererNotificationsParRendezVous(rendezVousId, page, size, sort);
    }

    @DeleteMapping("/{id}")
    public void supprimerNotification(@PathVariable Long id) {
        notificationService.supprimerNotification(id);
    }
}

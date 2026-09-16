package org.example.cliniquepro.service;

import org.example.cliniquepro.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {
    NotificationDTO save(NotificationDTO notificationDTO);
    NotificationDTO update(NotificationDTO notificationDTO);
    List<NotificationDTO> findAll();
    NotificationDTO findOne(Long id);
    void delete(Long id);
}

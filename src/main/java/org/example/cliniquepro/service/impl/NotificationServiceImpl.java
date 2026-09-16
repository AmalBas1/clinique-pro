package org.example.cliniquepro.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collections;

import org.example.cliniquepro.service.NotificationService;
import org.example.cliniquepro.dto.NotificationDTO;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public NotificationDTO save(NotificationDTO notificationDTO) { return null; }

    @Override
    public NotificationDTO update(NotificationDTO notificationDTO) { return null; }

    @Override
    public List<NotificationDTO> findAll() { return Collections.emptyList(); }

    @Override
    public NotificationDTO findOne(Long id) { return null; }

    @Override
    public void delete(Long id) { }
}

package org.example.cliniquepro.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.MessageDTO;
import org.example.cliniquepro.dto.NotificationDTO;
import org.example.cliniquepro.entity.Message;
import org.example.cliniquepro.entity.Notification;
import org.example.cliniquepro.entity.RendezVous;
import org.example.cliniquepro.enums.TypeNotification;
import org.example.cliniquepro.mapper.MessageMapper;
import org.example.cliniquepro.repository.MessageRepository;
import org.example.cliniquepro.repository.NotificationRepository;
import org.example.cliniquepro.repository.RendezVousRepository;
import org.example.cliniquepro.service.MessageService;
import org.example.cliniquepro.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final RendezVousRepository rendezVousRepository;
    private final NotificationRepository notificationRepository;
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    @Override
    public MessageDTO creerMessage(MessageDTO messageDTO) {
        if (messageDTO.getRendezVousId() == null) {
            throw new RuntimeException("Un message doit obligatoirement etre lie a un rendez-vous.");
        }
        RendezVous rendezVous = rendezVousRepository.findById(messageDTO.getRendezVousId())
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouve avec l'ID : " + messageDTO.getRendezVousId()));

        Message message = messageMapper.toEntity(messageDTO);
        if (message.getDateEnvoi() == null) {
            message.setDateEnvoi(LocalDateTime.now());
        }

        Message savedMessage = messageRepository.save(message);
        MessageDTO responseDTO = messageMapper.toDTO(savedMessage);
        responseDTO.setRendezVousId(rendezVous.getId());

        messagingTemplate.convertAndSend("/topic/rendez-vous/" + rendezVous.getId(), responseDTO);

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setType(TypeNotification.NOUVEAU_MESSAGE);
        notificationDTO.setRendezVousId(rendezVous.getId());
        notificationDTO.setContenu("Vous avez recu un nouveau message concernant votre rendez-vous : " + savedMessage.getContenu());
        try {
            NotificationDTO savedNotificationDTO = notificationService.creerNotification(notificationDTO);
            Notification notification = notificationRepository.findById(savedNotificationDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Notification creee introuvable."));
            savedMessage.setNotification(notification);
            messageRepository.save(savedMessage);
            responseDTO.setNotificationId(notification.getId());
        } catch (Exception e) {
            System.err.println("Erreur notification message : " + e.getMessage());
        }

        return responseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public MessageDTO recupererMessageParId(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message non trouve avec l'ID : " + id));
        return messageMapper.toDTO(message);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MessageDTO> recupererMessagesParRendezVous(Long rendezVousId, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sort), "id");
        Page<Message> messages = messageRepository.findByNotification_RendezVousId(rendezVousId, pageable);
        return messages.map(messageMapper::toDTO);
    }

    @Override
    public void supprimerMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new RuntimeException("Message non trouve avec l'ID : " + id);
        }
        messageRepository.deleteById(id);
    }

    @Override
    public long compterMessagesParPatient(Long patientId) {
        return messageRepository.countByNotification_RendezVous_PatientId(patientId);
    }

    @Override
    public long compterMessagesParMedecin(Long medecinId) {
        return messageRepository.countByNotification_RendezVous_MedecinId(medecinId);
    }
}

package org.example.cliniquepro.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.MessageDTO;
import org.example.cliniquepro.entity.Message;
import org.example.cliniquepro.entity.RendezVous;
import org.example.cliniquepro.mapper.MessageMapper;
import org.example.cliniquepro.repository.MessageRepository;
import org.example.cliniquepro.repository.RendezVousRepository;
import org.example.cliniquepro.service.MessageService;
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
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public MessageDTO creerMessage(MessageDTO messageDTO) {
        Message message = messageMapper.toEntity(messageDTO);

        if (messageDTO.getRendezVousId() != null) {
            RendezVous rendezVous = rendezVousRepository.findById(messageDTO.getRendezVousId())
                    .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec l'ID : " + messageDTO.getRendezVousId()));
            message.setRendezVous(rendezVous);
        } else {
            throw new RuntimeException("Un message doit obligatoirement être lié à un rendez-vous.");
        }

        if (message.getDateEnvoi() == null) {
            message.setDateEnvoi(LocalDateTime.now());
        }

        Message savedMessage = messageRepository.save(message);
        MessageDTO responseDTO = messageMapper.toDTO(savedMessage);

        messagingTemplate.convertAndSend(
                "/topic/rendez-vous/" + messageDTO.getRendezVousId(),
                responseDTO
        );

        return responseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public MessageDTO recupererMessageParId(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message non trouvé avec l'ID : " + id));
        return messageMapper.toDTO(message);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MessageDTO> recupererMessagesParRendezVous(Long rendezVousId, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sort), "id");
        Page<Message> messages = messageRepository.findByRendezVousId(rendezVousId, pageable);
        return messages.map(messageMapper::toDTO);
    }

    @Override
    public void supprimerMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new RuntimeException("Message non trouvé avec l'ID : " + id);
        }
        messageRepository.deleteById(id);
    }
}
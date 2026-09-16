package org.example.cliniquepro.service;

import org.example.cliniquepro.dto.MessageDTO;
import java.util.List;

public interface MessageService {
    MessageDTO creerMessage(MessageDTO messageDTO);
    MessageDTO recupererMessageParId(Long id);
    List<MessageDTO> recupererMessagesParRendezVous(Long rendezVousId);
    void supprimerMessage(Long id);
}

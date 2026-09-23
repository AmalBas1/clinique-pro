package org.example.cliniquepro.service;

import org.example.cliniquepro.dto.MessageDTO;
import org.springframework.data.domain.Page;

public interface MessageService {
    MessageDTO creerMessage(MessageDTO messageDTO);
    MessageDTO recupererMessageParId(Long id);
    Page<MessageDTO> recupererMessagesParRendezVous(Long rendezVousId, int page, int size, String sort);
    void supprimerMessage(Long id);
    long compterMessagesParMedecin(Long medecinId);
    long compterMessagesParPatient(Long patientId);
}

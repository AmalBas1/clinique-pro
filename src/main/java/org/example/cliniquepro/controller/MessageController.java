package org.example.cliniquepro.controller;

import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.MessageDTO;
import org.example.cliniquepro.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN', 'PATIENT')")
    public MessageDTO creerMessage(@RequestBody MessageDTO messageDTO) {
        return messageService.creerMessage(messageDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN', 'PATIENT')")
    public MessageDTO recupererMessageParId(@PathVariable Long id) {
        return messageService.recupererMessageParId(id);
    }

    @GetMapping("/rendez-vous/{rendezVousId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN', 'PATIENT')")
    public Page<MessageDTO> recupererMessagesParRendezVous(
            @PathVariable Long rendezVousId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sort) {
        return messageService.recupererMessagesParRendezVous(rendezVousId, page, size, sort);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN', 'PATIENT')")
    public void supprimerMessage(@PathVariable Long id) {
        messageService.supprimerMessage(id);
    }
}
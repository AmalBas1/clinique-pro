package org.example.cliniquepro.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

import org.example.cliniquepro.enums.StatutRendezVous;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVousDTO {
    private Long id;
    private LocalDateTime dateRendezVous;
    private StatutRendezVous statut;
    private Long patientId;
    private Long medecinId;
    private List<Long> messageId;
    private List<Long> notificationId;
}

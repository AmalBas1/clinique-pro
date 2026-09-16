package org.example.cliniquepro.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MessageDTO {
    private Long id;
    private String contenu;
    private LocalDateTime dateEnvoi;
    private String expediteurRole;
    private Long rendezVousId;
}

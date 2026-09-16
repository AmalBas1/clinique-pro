package org.example.cliniquepro.dto;

import lombok.*;
import java.time.LocalDateTime;
import org.example.cliniquepro.enums.TypeNotification;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class NotificationDTO {
    private Long id;
    private String contenu;
    private LocalDateTime dateCreation;
    private TypeNotification type;
    private Long rendezVousId;
}

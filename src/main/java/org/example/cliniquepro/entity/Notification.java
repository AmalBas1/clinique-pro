package org.example.cliniquepro.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.cliniquepro.enums.TypeNotification;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String contenu;

    private LocalDateTime dateCreation;

    @Enumerated(EnumType.STRING)
    private TypeNotification type;

    @ManyToOne
    @JoinColumn(name = "rendez_vous_id", nullable = false)
    private RendezVous rendezVous;
}

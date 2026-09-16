package org.example.cliniquepro.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.cliniquepro.enums.Role;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String contenu;

    private LocalDateTime dateEnvoi;

    private String expediteurRole;

    @ManyToOne
    @JoinColumn(name = "rendez_vous_id")
    private RendezVous rendezVous;
}

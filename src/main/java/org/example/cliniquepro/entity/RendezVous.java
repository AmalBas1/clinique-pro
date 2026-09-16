package org.example.cliniquepro.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.cliniquepro.enums.StatutRendezVous;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rendezvous")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateRendezVous;

    @Enumerated(EnumType.STRING)
    private StatutRendezVous statut;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;

    @OneToMany(mappedBy = "rendezVous", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "rendezVous", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();
}

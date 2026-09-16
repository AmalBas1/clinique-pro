package org.example.cliniquepro.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private LocalDate dateNaissance;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RendezVous> rendezVous = new ArrayList<>();
}

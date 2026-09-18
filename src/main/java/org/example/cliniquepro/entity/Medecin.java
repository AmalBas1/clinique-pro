package org.example.cliniquepro.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.cliniquepro.entity.RendezVous;
import org.example.cliniquepro.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medecins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medecin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String telephone;
    private String specialite;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

}

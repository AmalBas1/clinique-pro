package org.example.cliniquepro.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PatientDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private LocalDate dateNaissance;
    private Long userId;

}

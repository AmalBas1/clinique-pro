package org.example.cliniquepro.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MedecinDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String specialite;
    private Long userId;
}

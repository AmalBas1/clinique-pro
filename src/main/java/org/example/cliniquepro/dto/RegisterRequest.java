package org.example.cliniquepro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cliniquepro.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Email(message = "le format de l'email est invalide")
    @NotBlank(message = "l'email est obligatoire")
    private String email;

    @NotBlank(message = "le mot de passe est obligatoire")
    private String password;

    @NotBlank(message = "le nom est obligatoire")
    private String nom;

    @NotBlank(message = "le prenom est obligatoire")
    private String prenom;

    @NotNull(message = "le role est obligatoire")
    private Role role;
}

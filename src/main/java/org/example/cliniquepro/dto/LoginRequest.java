package org.example.cliniquepro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @Email(message = "le format de l'email est invalide")
    @NotBlank(message = "l'email est obligatoire")
    private String email;

    @NotBlank(message = "le mot de passe est obligatoire")
    private String password;
}

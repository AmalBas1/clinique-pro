package org.example.cliniquepro.dto;

import lombok.*;
import org.example.cliniquepro.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private Role role;
}

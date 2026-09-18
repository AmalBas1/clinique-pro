package org.example.cliniquepro.service.impl;

import org.example.cliniquepro.dto.UserDTO;
import org.example.cliniquepro.entity.User;
import org.example.cliniquepro.enums.Role;
import org.example.cliniquepro.repository.UserRepository;
import org.example.cliniquepro.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void createUser() {
        UserDTO dto = new UserDTO();
        dto.setEmail("jean@test.com");
        dto.setPassword("1234");
        dto.setNom("Jean");
        dto.setPrenom("Dupont");
        dto.setRole(Role.ADMIN);

        UserDTO resultat = userService.createUser(dto);

        assertNotNull(resultat.getId());
        assertEquals("jean@test.com", resultat.getEmail());
    }

    @Test
    void getUserById() {
        User user = new User();
        user.setEmail("luc@test.com");
        user.setPassword("1234");
        user.setNom("Luc");
        user.setPrenom("Martin");
        user.setRole(Role.PATIENT);
        userRepository.save(user);

        UserDTO resultat = userService.getUserById(user.getId());

        assertEquals("luc@test.com", resultat.getEmail());
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setEmail("old@test.com");
        user.setPassword("1234");
        user.setNom("AncienNom");
        user.setPrenom("Prenom");
        user.setRole(Role.PATIENT);
        userRepository.save(user);

        UserDTO dto = new UserDTO();
        dto.setEmail("new@test.com");
        dto.setPassword("5678");
        dto.setNom("NouveauNom");
        dto.setPrenom("Prenom");
        dto.setRole(Role.MEDECIN);

        UserDTO resultat = userService.updateUser(user.getId(), dto);

        assertEquals("NouveauNom", resultat.getNom());
    }
}

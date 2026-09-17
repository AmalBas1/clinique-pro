package org.example.cliniquepro.service.impl;

import org.example.cliniquepro.dto.MedecinDTO;
import org.example.cliniquepro.entity.Medecin;
import org.example.cliniquepro.entity.User;
import org.example.cliniquepro.enums.Role;
import org.example.cliniquepro.repository.MedecinRepository;
import org.example.cliniquepro.repository.UserRepository;
import org.example.cliniquepro.service.MedecinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class MedecinServiceImplTest {

    @Autowired
    private MedecinService medecinService;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void creerMedecin() {
        User user = new User();
        user.setEmail("medecin1@test.com");
        user.setPassword("1234");
        user.setNom("Martin");
        user.setPrenom("Alice");
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        MedecinDTO dto = new MedecinDTO();
        dto.setNom("Martin");
        dto.setPrenom("Alice");
        dto.setTelephone("0600000000");
        dto.setSpecialite("Cardiologie");
        dto.setUserId(user.getId());

        MedecinDTO resultat = medecinService.creerMedecin(dto);

        assertNotNull(resultat.getId());
        assertEquals("Martin", resultat.getNom());
    }

    @Test
    void recupererMedecinParId() {
        User user = new User();
        user.setEmail("medecin2@test.com");
        user.setPassword("1234");
        user.setNom("Lemoine");
        user.setPrenom("Sophie");
        user.setRole(Role.MEDECIN);
        userRepository.save(user);

        Medecin medecin = new Medecin(null, "Lemoine", "Sophie", "0612345678", "Pediatrie", user);
        medecinRepository.save(medecin);

        MedecinDTO resultat = medecinService.recupererMedecinParId(medecin.getId());

        assertEquals("Lemoine", resultat.getNom());
    }

    @Test
    void modifierMedecin() {
        User user = new User();
        user.setEmail("medecin3@test.com");
        user.setPassword("1234");
        user.setNom("Dupont");
        user.setPrenom("Jean");
        user.setRole(Role.MEDECIN);
        userRepository.save(user);

        Medecin medecin = new Medecin(null, "Dupont", "Jean", "0623456789", "Dermatologie", user);
        medecinRepository.save(medecin);

        MedecinDTO dto = new MedecinDTO();
        dto.setNom("NouveauNom");
        dto.setPrenom("Jean");
        dto.setTelephone("0623456789");
        dto.setSpecialite("Dermatologie");

        MedecinDTO resultat = medecinService.mettreAJourMedecin(medecin.getId(), dto);

        assertEquals("NouveauNom", resultat.getNom());
    }
}

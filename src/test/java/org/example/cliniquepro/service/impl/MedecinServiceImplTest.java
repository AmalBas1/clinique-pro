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

        Medecin medecin = new Medecin(null, "0612345678", "Pediatrie", user, true);
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

        Medecin medecin = new Medecin(null, "0623456789", "Dermatologie", user, true);
        medecinRepository.save(medecin);

        MedecinDTO dto = new MedecinDTO();
        dto.setNom("NouveauNom");
        dto.setPrenom("Jean");
        dto.setTelephone("0623456789");
        dto.setSpecialite("Dermatologie");

        MedecinDTO resultat = medecinService.mettreAJourMedecin(medecin.getId(), dto);

        assertEquals("NouveauNom", resultat.getNom());
        medecinRepository.flush();
        assertEquals("NouveauNom", userRepository.findById(user.getId()).orElseThrow().getNom());
    }

    @Test
    void lireIdentiteDepuisUtilisateur() {
        User user = new User();
        user.setEmail("identite-medecin@test.com");
        user.setNom("Initial");
        user.setPrenom("Avant");
        user.setRole(Role.MEDECIN);
        userRepository.save(user);

        Medecin profile = new Medecin();
        profile.setUser(user);
        medecinRepository.save(profile);

        user.setNom("ModifieDansUser");
        user.setPrenom("Apres");
        userRepository.saveAndFlush(user);

        MedecinDTO dto = medecinService.recupererMedecinParId(profile.getId());
        assertEquals("ModifieDansUser", dto.getNom());
        assertEquals("Apres", dto.getPrenom());
        assertEquals(user.getId(), dto.getUserId());
    }

    @Autowired
    private jakarta.persistence.EntityManager entityManager;

    @Test
    void supprimerProfilLieUtilisateur() {
        User user = new User();
        user.setEmail("suppression-medecin@test.com");
        user.setRole(Role.MEDECIN);
        userRepository.save(user);
        Medecin profile = new Medecin();
        profile.setUser(user);
        user.setMedecin(profile);
        medecinRepository.saveAndFlush(profile);
        Long profileId = profile.getId();
        Long userId = user.getId();
        entityManager.clear();

        medecinService.supprimerMedecin(profileId);
        medecinRepository.flush();
        entityManager.clear();

        org.junit.jupiter.api.Assertions.assertFalse(medecinRepository.existsById(profileId));
        org.junit.jupiter.api.Assertions.assertTrue(userRepository.existsById(userId));
    }
}

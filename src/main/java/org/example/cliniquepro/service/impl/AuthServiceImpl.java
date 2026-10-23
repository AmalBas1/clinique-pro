package org.example.cliniquepro.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.AuthResponse;
import org.example.cliniquepro.dto.LoginRequest;
import org.example.cliniquepro.dto.RegisterRequest;
import org.example.cliniquepro.dto.UserDTO;
import org.example.cliniquepro.entity.Medecin;
import org.example.cliniquepro.entity.Patient;
import org.example.cliniquepro.entity.User;
import org.example.cliniquepro.enums.Role;
import org.example.cliniquepro.mapper.UserMapper;
import org.example.cliniquepro.repository.MedecinRepository;
import org.example.cliniquepro.repository.PatientRepository;
import org.example.cliniquepro.repository.UserRepository;
import org.example.cliniquepro.security.JwtUtils;
import org.example.cliniquepro.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final MedecinRepository medecinRepository;
    private final PatientRepository patientRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Cet e-mail est deja utilise !");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        if (request.getRole() == Role.MEDECIN) {
            Medecin medecin = new Medecin();
            medecin.setUser(savedUser);
            medecin.setTelephone(request.getTelephone());
            medecin.setSpecialite(request.getSpecialite());
            medecin.setDisponible(true);
            medecinRepository.save(medecin);

        } else if (request.getRole() == Role.PATIENT) {
            Patient patient = new Patient();
            patient.setUser(savedUser);
            patient.setTelephone(request.getTelephone());
            patient.setAdresse(request.getAdresse());
            patient.setDateNaissance(request.getDateNaissance());
            patientRepository.save(patient);
        }

        String token = jwtUtils.genererToken(savedUser.getEmail(), savedUser.getRole());
        UserDTO userDTO = userMapper.toDTO(savedUser);

        return new AuthResponse(token, userDTO, savedUser.getRole());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));
        String token = jwtUtils.genererToken(user.getEmail(), user.getRole());
        UserDTO userDTO = userMapper.toDTO(user);

        return new AuthResponse(token, userDTO, user.getRole());
    }
}

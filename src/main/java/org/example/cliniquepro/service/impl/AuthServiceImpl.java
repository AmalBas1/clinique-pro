package org.example.cliniquepro.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.AuthResponse;
import org.example.cliniquepro.dto.LoginRequest;
import org.example.cliniquepro.dto.RegisterRequest;
import org.example.cliniquepro.dto.UserDTO;
import org.example.cliniquepro.entity.User;
import org.example.cliniquepro.mapper.UserMapper;
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

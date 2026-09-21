package org.example.cliniquepro.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.cliniquepro.dto.AuthResponse;
import org.example.cliniquepro.dto.LoginRequest;
import org.example.cliniquepro.dto.RegisterRequest;
import org.example.cliniquepro.service.AuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/logout")
    public String logout() {
        return "Deconnexion cote client: supprimez le token JWT du navigateur.";
    }
}

package org.example.cliniquepro.service;

import org.example.cliniquepro.dto.AuthResponse;
import org.example.cliniquepro.dto.LoginRequest;
import org.example.cliniquepro.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}

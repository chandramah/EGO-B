package com.smartretails.backend.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartretails.backend.dto.LoginRequest;
import com.smartretails.backend.dto.LoginResponse;
import com.smartretails.backend.dto.SignupRequest;
import com.smartretails.backend.dto.SignupResponse;
import com.smartretails.backend.entity.User;
import com.smartretails.backend.repository.UserRepository;
import com.smartretails.backend.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int LOCK_MINUTES = 10;

    public LoginResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid Credentials"));

        if (user.getLockedUntil() != null && user.getLockedUntil().isAfter(LocalDateTime.now())) {
            throw new BadCredentialsException("Account locked. Try again later");
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            userRepository.updateFailedLoginAttempts(user.getUsername(), 0);
            userRepository.updateLastLogin(user.getUsername(), LocalDateTime.now());

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

            String token = jwtUtil.generateToken(userDetails.getUsername(), user.getRole().name());

            return LoginResponse.builder()
                    .token(token)
                    .refreshToken(token)
                    .username(user.getUsername())
                    .role(user.getRole().name())
                    .expiresIn(86400L)
                    .build();

        } catch (BadCredentialsException ex) {
            int attempts = (user.getFailedLoginAttempts() == null ? 0 : user.getFailedLoginAttempts()) + 1;
            userRepository.updateFailedLoginAttempts(user.getUsername(), attempts);
            if (attempts >= MAX_FAILED_ATTEMPTS)
                userRepository.lockUser(user.getUsername(), LocalDateTime.now().plusMinutes(LOCK_MINUTES));
            throw ex;
        }
    }

    public SignupResponse register(SignupRequest request) {
        String username = request.getUsername();
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already taken");
        }

        // Resolve role. Default to CASHIER if missing/invalid.
        User.Role role;
        try {
            if (request.getRole() == null || request.getRole().isBlank()) {
                role = User.Role.ROLE_CASHIER;
            } else {
                String normalized = request.getRole().trim().toUpperCase();
                if (!normalized.startsWith("ROLE_")) {
                    normalized = "ROLE_" + normalized;
                }
                role = User.Role.valueOf(normalized);
            }
        } catch (IllegalArgumentException e) {
            role = User.Role.ROLE_CASHIER;
        }

        User newUser = User.builder()
                .username(username)
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .isActive(true)
                .failedLoginAttempts(0)
                .build();

        User saved = userRepository.save(newUser);

        return SignupResponse.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .role(saved.getRole().name())
                .build();
    }
}

package com.smartretails.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartretails.backend.dto.UserRequestDto;
import com.smartretails.backend.dto.UserResponseDto;
import com.smartretails.backend.entity.User;
import com.smartretails.backend.entity.User.Role;
import com.smartretails.backend.repository.UserRepository;
import com.smartretails.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDto> getAllUsers() {

        return userRepository.findAll().stream()
                .map(user -> UserResponseDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .role(user.getRole().name())
                        .password(user.getPasswordHash())
                        .build())
                .toList();
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> UserResponseDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .role(user.getRole().name())
                        .password(user.getPasswordHash())
                        .build())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        Role userRole = Role.valueOf("ROLE_" + userRequestDto.getRole().toUpperCase());
        User user = User.builder()
                .username(userRequestDto.getUsername())
                .passwordHash(passwordEncoder.encode(userRequestDto.getPassword()))
                .role(userRole)
                .build();
        userRepository.save(user);

        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role("ROLE_" + user.getRole().name())
                .build();
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto user) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setUsername(user.getUsername());
                    if (user.getPassword() != null && !user.getPassword().isBlank()) {
                        existing.setPasswordHash(passwordEncoder.encode(user.getPassword()));
                    }
                    existing.setRole(Role.valueOf("ROLE_" + user.getRole().toUpperCase()));

                    userRepository.save(existing);
                    return UserResponseDto.builder()
                            .id(existing.getId())
                            .username(existing.getUsername())
                            .role(existing.getRole().name())
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}

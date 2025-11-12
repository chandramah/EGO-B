package com.smartretails.backend.service;

import java.util.List;

import com.smartretails.backend.dto.UserRequestDto;
import com.smartretails.backend.dto.UserResponseDto;

public interface UserService {

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    UserResponseDto createUser(UserRequestDto user);

    UserResponseDto updateUser(Long id, UserRequestDto user);

    void deleteUser(Long id);
}

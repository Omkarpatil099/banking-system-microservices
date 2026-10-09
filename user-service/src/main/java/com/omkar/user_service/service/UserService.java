package com.omkar.user_service.service;

import com.omkar.user_service.dto.UserLoginDto;
import com.omkar.user_service.dto.UserRegisterDto;
import com.omkar.user_service.dto.UserResponseDto;

public interface UserService {

    UserResponseDto registerUser(UserRegisterDto dto);

    UserResponseDto loginUser(UserLoginDto dto);

    UserResponseDto getUserById(Long userId);
}
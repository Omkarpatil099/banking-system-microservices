package com.omkar.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.omkar.user_service.dto.UserLoginDto;
import com.omkar.user_service.dto.UserRegisterDto;
import com.omkar.user_service.dto.UserResponseDto;
import com.omkar.user_service.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(Authentication authentication) {

        String email = authentication.getName();

        return new ResponseEntity<>(
                "Welcome " + email + "! You are authenticated.",
                HttpStatus.OK
        );
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(
            @PathVariable Long userId) {

        UserResponseDto response = userService.getUserById(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(
            @RequestBody UserRegisterDto dto) {

        UserResponseDto response = userService.registerUser(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(
            @RequestBody UserLoginDto dto) {

        UserResponseDto response = userService.loginUser(dto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
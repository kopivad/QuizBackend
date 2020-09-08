package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.dto.LoginDto;
import com.kopivad.quizzes.dto.RegisterDto;
import com.kopivad.quizzes.dto.UserDto;
import com.kopivad.quizzes.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("login")
    public UserDto login(@RequestBody LoginDto dto) {
        return authService.login(dto);
    }

    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody RegisterDto dto) {
        if (authService.register(dto)) return ResponseEntity.status(HttpStatus.OK).build();
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.dto.LoginUserDto;
import com.kopivad.quizzes.dto.RegisterUserDto;
import com.kopivad.quizzes.dto.UserTokenDto;
import com.kopivad.quizzes.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<UserTokenDto> login(@RequestBody LoginUserDto dto) {
        Optional<UserTokenDto> userTokenDto = authService.login(dto);
        return userTokenDto
                .map(tokenDto -> ResponseEntity.status(HttpStatus.OK).body(tokenDto))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody RegisterUserDto dto) {
        if (authService.register(dto)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

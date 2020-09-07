package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.domain.Role;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.LoginDto;
import com.kopivad.quizzes.dto.RegisterDto;
import com.kopivad.quizzes.dto.UserDto;
import com.kopivad.quizzes.mapper.UserMapper;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.service.AuthService;
import com.kopivad.quizzes.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDto login(LoginDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String token = jwtUtils.generateToken(user);
        return userMapper
                .toDto(user)
                .toBuilder()
                .token(token)
                .build();
    }

    @Override
    public boolean register(RegisterDto dto) {
        if (userRepository.isUserExistsByEmail(dto.getEmail())) return false;
        User user = User
                .builder()
                .email(dto.getEmail())
                .name(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .creationDate(LocalDateTime.now())
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return true;
    }
}

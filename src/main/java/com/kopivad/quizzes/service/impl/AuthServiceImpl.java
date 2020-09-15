package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.LoginUserDto;
import com.kopivad.quizzes.dto.UserDto;
import com.kopivad.quizzes.dto.UserTokenDto;
import com.kopivad.quizzes.service.AuthService;
import com.kopivad.quizzes.service.JwtService;
import com.kopivad.quizzes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserTokenDto> login(LoginUserDto dto) {
        Optional<User> user = userService.getByEmail(dto.getEmail());
        if (user.isPresent()) {
            String dtoPassword = dto.getPassword();
            String userPassword = user.get().getPassword();
            if (passwordEncoder.matches(dtoPassword, userPassword)) {
                String token = jwtService.generateToken(user.get());
                UserTokenDto userTokenDto = new UserTokenDto(user.get(), token);
                return Optional.of(userTokenDto);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean register(UserDto dto) {
        if (userService.getByEmail(dto.getEmail()).isEmpty()) {
            return userService.save(dto);
        }
        return false;
    }
}

package com.kopivad.quizzes.service;

import com.kopivad.quizzes.dto.LoginUserDto;
import com.kopivad.quizzes.dto.UserDto;
import com.kopivad.quizzes.dto.UserTokenDto;

import java.util.Optional;

public interface AuthService {
    Optional<UserTokenDto> login(LoginUserDto dto);

    boolean register(UserDto dto);
}

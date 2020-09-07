package com.kopivad.quizzes.service;

import com.kopivad.quizzes.dto.LoginDto;
import com.kopivad.quizzes.dto.RegisterDto;
import com.kopivad.quizzes.dto.UserDto;

public interface AuthService {
    UserDto login(LoginDto dto);

    boolean register(RegisterDto dto);
}

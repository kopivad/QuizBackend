package com.kopivad.quizzes.dto;

import com.kopivad.quizzes.domain.User;
import lombok.Value;

@Value
public class UserTokenDto {
    User user;
    String token;
}

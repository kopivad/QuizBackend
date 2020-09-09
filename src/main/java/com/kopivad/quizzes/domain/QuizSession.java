package com.kopivad.quizzes.domain;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class QuizSession {
    long id;
    Long quizId;
    Long userId;
    LocalDateTime date;
}

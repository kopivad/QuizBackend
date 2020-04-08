package com.kopivad.quizzes.dto;

import lombok.Value;

@Value
public class QuizDto {
    String title;
    boolean active;
    String description;
    Long userId;
}

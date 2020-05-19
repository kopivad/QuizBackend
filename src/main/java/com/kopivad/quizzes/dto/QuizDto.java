package com.kopivad.quizzes.dto;

import lombok.Value;

import java.util.List;

@Value
public class QuizDto {
    String title;
    boolean active;
    String description;
    Long userId;
    List<QuestionDto> questions;
}

package com.kopivad.quizzes.dto;

import lombok.Data;

@Data
public class QuizDto {
    private String title;
    private boolean active;
    private String description;
    private Long userId;
}

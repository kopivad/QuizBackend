package com.kopivad.quizzes.dto;

import com.kopivad.quizzes.domain.QuestionType;
import lombok.Value;

@Value
public class QuestionDto {
    String title;
    QuestionType type;
    Long quizId;
}

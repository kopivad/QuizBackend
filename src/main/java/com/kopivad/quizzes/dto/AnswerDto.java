package com.kopivad.quizzes.dto;

import com.kopivad.quizzes.domain.AnswerType;
import lombok.Value;

@Value
public class AnswerDto {
    String text;
    boolean isRight;
    AnswerType type;
    Long questionId;
}

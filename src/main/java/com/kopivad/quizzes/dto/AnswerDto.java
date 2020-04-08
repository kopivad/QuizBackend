package com.kopivad.quizzes.dto;

import lombok.Value;

@Value
public class AnswerDto {
    String text;

    boolean isRight;

    Long questionId;
}

package com.kopivad.quizzes.domain;

import lombok.Value;

@Value
public class Answer {
    Long id;
    String body;
    boolean isRight;
    Long questionId;
}

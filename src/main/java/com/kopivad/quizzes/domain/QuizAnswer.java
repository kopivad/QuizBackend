package com.kopivad.quizzes.domain;

import lombok.Value;

@Value
public class QuizAnswer {
    long id;
    Long questionId;
    Long sessionId;
    Long answerId;
}

package com.kopivad.quizzes.domain;

import lombok.Value;

@Value
public class Question {
    Long id;
    String title;
    int value;
    QuestionType type;
    Long quizId;
}

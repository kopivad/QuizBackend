package com.kopivad.quizzes.domain;

import lombok.*;

import java.util.List;

@Value
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
public class Question {
    Long id;
    String title;
    QuestionType type;
    List<Answer> answers;
    Quiz quiz;
}

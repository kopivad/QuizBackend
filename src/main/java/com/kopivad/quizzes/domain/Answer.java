package com.kopivad.quizzes.domain;

import lombok.*;

@Value
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
public class Answer {
    Long id;
    String body;
    boolean isRight;
    Question question;
}

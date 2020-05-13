package com.kopivad.quizzes.domain;

import lombok.*;

@Value
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
public class Answer {
    Long id;
    String text;
    boolean isRight;
    Question question;
}

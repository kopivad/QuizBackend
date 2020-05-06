package com.kopivad.quizzes.domain;

import lombok.*;

@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private Long id;
    private String text;
    private AnswerType type;
    private boolean isRight;
    private Question question;
}

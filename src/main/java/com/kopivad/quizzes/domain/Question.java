package com.kopivad.quizzes.domain;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(of = {"id"})
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private Long id;
    private String title;
    private List<Answer> answers;
    private Quiz quiz;
}

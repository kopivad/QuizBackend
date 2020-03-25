package com.kopivad.quizzes.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @Id
    private Long id;

    private String text;

    private boolean isRight;

    @ManyToOne
    private Question question;
}

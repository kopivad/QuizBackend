package com.kopivad.quizzes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@EqualsAndHashCode(of = {"id"})
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
    @JsonIgnoreProperties(value = {"quiz", "answers"})
    private Question question;
}

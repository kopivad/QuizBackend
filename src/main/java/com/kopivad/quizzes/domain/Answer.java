package com.kopivad.quizzes.domain;

import lombok.*;

import javax.persistence.*;

import static org.springframework.data.jpa.repository.EntityGraph.*;

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
    private Question question;
}

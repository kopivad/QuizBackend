package com.kopivad.quizzes.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "answers")
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "is_right", nullable = false)
    private boolean isRight;

    @ManyToOne()
    private Question question;
}

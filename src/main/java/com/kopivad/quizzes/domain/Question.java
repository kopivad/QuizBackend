package com.kopivad.quizzes.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @ManyToOne
    private Quiz quiz;
}

package com.kopivad.quizzes.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @OneToMany()
    private List<Answer> answers;


    @ManyToOne
    private Quiz quiz;
}

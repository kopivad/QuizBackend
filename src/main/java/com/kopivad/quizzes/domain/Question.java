package com.kopivad.quizzes.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@EqualsAndHashCode(of = {"id"})
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    private Long id;

    private String title;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @ManyToOne
    private Quiz quiz;
}

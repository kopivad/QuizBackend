package com.kopivad.quizzes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @OneToMany
    @JsonIgnoreProperties(value = {"question"})
    private List<Answer> answers;


    @ManyToOne
    @JsonIgnoreProperties(value = {"author", "questions"})
    private Quiz quiz;
}

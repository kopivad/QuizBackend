package com.kopivad.testingsystem.domain;

import lombok.*;

import javax.persistence.*;

@Entity
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

//    @OneToMany()
//    private Set<Answer> answers;


    @ManyToOne
    private Quiz quiz;
}

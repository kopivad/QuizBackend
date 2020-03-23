package com.kopivad.testingsystem.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {
    @Id
    private Long id;
    private String title;
    private String description;
    private boolean active;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp creationDate;

    @ManyToOne
    private User author;

//    @OneToMany(mappedBy = "quiz", orphanRemoval = true)
//    private Set<Question> questions;
}

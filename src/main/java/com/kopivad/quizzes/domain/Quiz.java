package com.kopivad.quizzes.domain;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Quiz {
    Long id;
    String title;
    String description;
    int total;
    boolean active;
    LocalDateTime creationDate;
    Long authorId;
}

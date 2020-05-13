package com.kopivad.quizzes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
public class User {
    Long id;
    String name;
    String email;
    String password;
    Role role;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime creationDate;
    @JsonIgnoreProperties({"author", "questions"})
    List<Quiz> quizzes;
}

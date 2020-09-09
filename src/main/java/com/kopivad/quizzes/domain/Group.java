package com.kopivad.quizzes.domain;

import lombok.Value;

@Value
public class Group {
    long id;
    String name;
    String joinCode;
}

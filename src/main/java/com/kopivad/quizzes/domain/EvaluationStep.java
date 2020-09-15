package com.kopivad.quizzes.domain;

import lombok.Value;

@Value
public class EvaluationStep {
    long id;
    int minTotal;
    int maxTotal;
    String rating;
    Long quizId;

}

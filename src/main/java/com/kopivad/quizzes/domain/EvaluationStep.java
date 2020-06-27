package com.kopivad.quizzes.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
public class EvaluationStep {
    long id;
    int minTotal;
    String rating;
    Quiz quiz;
}

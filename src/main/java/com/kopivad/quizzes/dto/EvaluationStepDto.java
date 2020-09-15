package com.kopivad.quizzes.dto;

import lombok.Value;

@Value
public class EvaluationStepDto {
    Integer minTotal;
    Integer maxTotal;
    String rating;
    Long quizId;
}

package com.kopivad.quizzes.dto;

import lombok.Value;

@Value
public class SaveEvaluationStepDto {
    Integer minTotal;
    Integer maxTotal;
    String rating;
    Long quizId;
}

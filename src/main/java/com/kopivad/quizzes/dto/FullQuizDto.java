package com.kopivad.quizzes.dto;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.Quiz;
import lombok.Value;

import java.util.List;

@Value
public class FullQuizDto {
    Quiz quiz;
    List<FullQuestionDto> questions;
    List<EvaluationStep> steps;
}

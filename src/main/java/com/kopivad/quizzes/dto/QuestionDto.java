package com.kopivad.quizzes.dto;

import com.kopivad.quizzes.domain.QuestionType;
import lombok.Value;

import java.util.List;

@Value
public class QuestionDto {
    String title;
    QuestionType type;
    Long quizId;
    List<AnswerDto> answers;
}

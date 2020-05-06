package com.kopivad.quizzes.dto;

import com.kopivad.quizzes.domain.QuestionType;
import lombok.Value;

import java.util.List;

@Value
public class FullQuestionDto {
    String title;
    QuestionType type;
    List<FullAnswerDto> answers;
}

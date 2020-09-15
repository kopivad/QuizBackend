package com.kopivad.quizzes.dto;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import lombok.Value;

import java.util.List;

@Value
public class FullQuestionDto {
    Question question;
    List<Answer> answers;
}

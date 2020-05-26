package com.kopivad.quizzes.form;

import com.kopivad.quizzes.domain.QuestionType;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class QuestionForm {
    String title;
    QuestionType type;
    Long quizId;
    List<AnswerForm> answers;
}

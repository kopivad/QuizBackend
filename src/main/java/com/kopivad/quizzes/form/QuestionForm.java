package com.kopivad.quizzes.form;

import com.kopivad.quizzes.domain.QuestionType;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class QuestionForm {
    String title;
    QuestionType type;
    int value;
    Long quizId;
    List<AnswerForm> answers;
}

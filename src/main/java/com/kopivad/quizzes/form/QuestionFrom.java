package com.kopivad.quizzes.form;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.QuestionType;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.utils.FormUtils;
import lombok.Value;

import java.util.List;

@Value
public class QuestionFrom {
    String title;
    QuestionType type;
    Long quizId;
    List<AnswerForm> answers;

    public Question toQuestion() {
        return Question
                .builder()
                .title(title)
                .type(type)
                .quiz(Quiz.builder().id(quizId).build())
                .answers(FormUtils.toAnswerList(answers))
                .build();
    }
}

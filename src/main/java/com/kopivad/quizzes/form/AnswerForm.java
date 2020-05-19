package com.kopivad.quizzes.form;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import lombok.Value;

@Value
public class AnswerForm {
    String body;
    boolean right;
    Long questionId;

    public Answer toAnswer() {
        return Answer
                .builder()
                .body(body)
                .isRight(right)
                .question(Question.builder().id(questionId).build())
                .build();
    }
}

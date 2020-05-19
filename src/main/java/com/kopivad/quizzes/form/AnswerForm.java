package com.kopivad.quizzes.form;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;

public class AnswerForm {
    String body;
    boolean isRight;
    Long questionId;

    public Answer toAnswer() {
        return Answer
                .builder()
                .body(body)
                .isRight(isRight)
                .question(Question.builder().id(questionId).build())
                .build();
    }
}

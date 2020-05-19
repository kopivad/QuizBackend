package com.kopivad.quizzes.form;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.utils.FormUtils;
import lombok.Value;

import java.util.List;

@Value
public class QuizForm {
    String title;
    String description;
    List<QuestionFrom> questions;
    Long authorId;

    public Quiz toQuiz() {
        return Quiz
                .builder()
                .title(title)
                .description(description)
                .author(User.builder().id(authorId).build())
                .questions(FormUtils.toQuestionList(questions))
                .build();
    }
}

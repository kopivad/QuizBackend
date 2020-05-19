package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.form.AnswerForm;
import com.kopivad.quizzes.form.QuestionFrom;

import java.util.List;
import java.util.stream.Collectors;

public class FormUtils {

    public static List<Question> toQuestionList(List<QuestionFrom> questions) {
        return questions
                .stream()
                .map(questionFrom -> questionFrom.toQuestion())
                .collect(Collectors.toUnmodifiableList());
    }

    public static List<Answer> toAnswerList(List<AnswerForm> answers) {
        return answers
                .stream()
                .map(answerForm -> answerForm.toAnswer())
                .collect(Collectors.toUnmodifiableList());
    }
}

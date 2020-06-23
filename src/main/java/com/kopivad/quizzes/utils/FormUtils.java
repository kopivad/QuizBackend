package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.form.AnswerForm;
import com.kopivad.quizzes.form.QuestionForm;
import com.kopivad.quizzes.form.QuizForm;
import com.kopivad.quizzes.form.UserForm;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FormUtils {

    public static List<Question> toQuestionList(List<QuestionForm> questions) {
        if (ObjectUtils.isEmpty(questions))
            return new ArrayList<>();
        return questions
                .stream()
                .map(FormUtils::toQuestion)
                .collect(Collectors.toUnmodifiableList());
    }

    public static List<Answer> toAnswerList(List<AnswerForm> answers) {
        if (ObjectUtils.isEmpty(answers))
            return new ArrayList<>();
        return answers
                .stream()
                .map(FormUtils::toAnswer)
                .collect(Collectors.toUnmodifiableList());
    }

    public static User toUser(UserForm form) {
        return User
                .builder()
                .email(form.getEmail())
                .name(form.getName())
                .password(form.getPassword())
                .role(form.getRole())
                .build();
    }

    public static Quiz toQuiz(QuizForm form) {
        return Quiz
                .builder()
                .title(form.getTitle())
                .description(form.getDescription())
                .total(form.getTotal())
                .author(User.builder().id(form.getAuthorId()).build())
                .questions(FormUtils.toQuestionList(form.getQuestions()))
                .build();
    }

    public static Question toQuestion(QuestionForm from) {
        return Question
                .builder()
                .title(from.getTitle())
                .value(from.getValue())
                .type(from.getType())
                .quiz(Quiz.builder().id(from.getQuizId()).build())
                .answers(FormUtils.toAnswerList(from.getAnswers()))
                .build();
    }

    public static Answer toAnswer(AnswerForm form) {
        return Answer
                .builder()
                .body(form.getBody())
                .isRight(form.isRight())
                .question(Question.builder().id(form.getQuestionId()).build())
                .build();
    }
}

package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.form.AnswerForm;
import com.kopivad.quizzes.form.QuestionForm;
import com.kopivad.quizzes.form.QuizForm;
import com.kopivad.quizzes.form.UserForm;

import java.util.List;
import java.util.stream.Collectors;

public class FormUtils {

    public static List<Question> toQuestionList(List<QuestionForm> questions) {
        return questions
                .stream()
                .map(questionForm -> toQuestion(questionForm))
                .collect(Collectors.toUnmodifiableList());
    }

    public static List<Answer> toAnswerList(List<AnswerForm> answers) {
        return answers
                .stream()
                .map(answerForm -> toAnswer(answerForm))
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
                .author(User.builder().id(form.getAuthorId()).build())
                .questions(FormUtils.toQuestionList(form.getQuestions()))
                .build();
    }

    public static Question toQuestion(QuestionForm from) {
        return Question
                .builder()
                .title(from.getTitle())
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

    public static UserForm toUserFrom(User user) {
        return UserForm
                .builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static QuizForm toQuizFrom(Quiz quiz) {
        QuizForm quizForm = QuizForm
                .builder()
                .description(quiz.getDescription())
                .title(quiz.getTitle())
                .authorId(quiz.getAuthor().getId())
                .questions(toQuestionFormList(quiz.getQuestions()))
                .build();
        return quizForm;
    }

    public static QuestionForm toQuestionForm(Question question) {
        return QuestionForm
                .builder()
                .title(question.getTitle())
                .type(question.getType())
                .quizId(question.getQuiz().getId())
                .answers(FormUtils.toAnswerFormList(question.getAnswers()))
                .build();
    }

    public static List<QuestionForm> toQuestionFormList(List<Question> questions) {
        return questions
                .stream()
                .map(question -> toQuestionForm(question))
                .collect(Collectors.toUnmodifiableList());
    }

    public static List<AnswerForm> toAnswerFormList(List<Answer> answers) {
        return answers
                .stream()
                .map(answer -> toAnswerForm(answer))
                .collect(Collectors.toUnmodifiableList());
    }

    public static AnswerForm toAnswerForm(Answer answer) {
        return AnswerForm
                .builder()
                .body(answer.getBody())
                .questionId(answer.getQuestion().getId())
                .right(answer.isRight())
                .build();
    }
}

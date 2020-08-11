package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.*;
import com.kopivad.quizzes.domain.api.ApiClient;
import com.kopivad.quizzes.domain.db.tables.records.QuizAnswersRecord;
import org.apache.commons.lang3.ObjectUtils;
import org.jooq.Record;
import org.jooq.RecordMapper;

import static com.kopivad.quizzes.domain.db.tables.Answers.ANSWERS;
import static com.kopivad.quizzes.domain.db.tables.ApiClients.API_CLIENTS;
import static com.kopivad.quizzes.domain.db.tables.EvaluationSteps.EVALUATION_STEPS;
import static com.kopivad.quizzes.domain.db.tables.Groups.GROUPS;
import static com.kopivad.quizzes.domain.db.tables.Questions.QUESTIONS;
import static com.kopivad.quizzes.domain.db.tables.QuizAnswers.QUIZ_ANSWERS;
import static com.kopivad.quizzes.domain.db.tables.QuizHistories.QUIZ_HISTORIES;
import static com.kopivad.quizzes.domain.db.tables.QuizSessions.QUIZ_SESSIONS;
import static com.kopivad.quizzes.domain.db.tables.Quizzes.QUIZZES;
import static com.kopivad.quizzes.domain.db.tables.Usr.USR;

public class RecordMappers {

    public static RecordMapper<Record, User> getUserFromRecordMapper() {
        return record -> User
                .builder()
                .id(record.getValue(USR.ID))
                .name(record.getValue(USR.NAME))
                .email(record.getValue(USR.EMAIL))
                .role(Role.valueOf(record.getValue(USR.ROLE)))
                .creationDate(record.getValue(USR.CREATION_DATE).toLocalDateTime())
                .password(record.getValue(USR.PASSWORD))
                .group(getGroupFromUserIfNotEmpty(record))
                .build();
    }

    private static Group getGroupFromUserIfNotEmpty(Record record) {
        if (ObjectUtils.isNotEmpty(record.getValue(USR.GROUP_ID)))
            return Group.builder().id(record.getValue(USR.GROUP_ID)).build();
        else
            return Group.builder().build();

    }

    public static RecordMapper<Record, Answer> getAnswerFromRecordMapper() {
        return record -> Answer
                .builder()
                .id(record.getValue(ANSWERS.ID))
                .body(record.getValue(ANSWERS.BODY))
                .question(Question.builder().id(record.getValue(ANSWERS.QUESTION_ID)).build())
                .isRight(record.getValue(ANSWERS.IS_RIGHT))
                .build();
    }

    public static RecordMapper<Record, ApiClient> getApiClientFromRecord() {
        return record -> ApiClient
                .builder()
                .id(record.getValue(API_CLIENTS.ID))
                .username(record.getValue(API_CLIENTS.USERNAME))
                .password(record.getValue(API_CLIENTS.PASSWORD))
                .build();
    }

    public static RecordMapper<Record, EvaluationStep> getEvaluationStepsRecordEvaluationStepRecordMapper() {
        return r -> EvaluationStep
                .builder()
                .quiz(Quiz.builder().id(r.getValue(EVALUATION_STEPS.QUIZ_ID)).build())
                .rating(r.getValue(EVALUATION_STEPS.RATING))
                .id(r.getValue(EVALUATION_STEPS.ID))
                .minTotal(r.getValue(EVALUATION_STEPS.MINTOTAL))
                .maxTotal(r.getValue(EVALUATION_STEPS.MAXTOTAL))
                .build();
    }

    public static RecordMapper<Record, Group> getGroupsRecordGroupRecordMapper() {
        return r -> Group
                .builder()
                .id(r.getValue(GROUPS.ID))
                .name(r.getValue(GROUPS.NAME))
                .build();
    }

    public static RecordMapper<Record, Question> getQuestionFromRecordMapper() {
        return record -> Question
                .builder()
                .id(record.getValue(QUESTIONS.ID))
                .type(QuestionType.valueOf(record.getValue(QUESTIONS.TYPE)))
                .value(record.getValue(QUESTIONS.VALUE))
                .title(record.getValue(QUESTIONS.TITLE))
                .quiz(Quiz.builder().id(record.getValue(QUESTIONS.QUIZ_ID)).build())
                .build();
    }

    public static RecordMapper<QuizAnswersRecord, QuizAnswer> getQuizAnswersRecordQuizAnswerRecordMapper() {
        return r -> QuizAnswer
                .builder()
                .id(r.getValue(QUIZ_ANSWERS.ID))
                .answer(Answer.builder().id(r.getValue(QUIZ_ANSWERS.ANSWER_ID)).build())
                .question(Question.builder().id(r.getValue(QUIZ_ANSWERS.QUESTION_ID)).build())
                .session(QuizSession.builder().id(r.getValue(QUIZ_ANSWERS.SESSION_ID)).build())
                .build();
    }

    public static RecordMapper<Record, QuizHistory> getRecordQuizHistoryRecordMapper() {
        return r -> QuizHistory
                .builder()
                .id(r.getValue(QUIZ_HISTORIES.ID))
                .rating(r.getValue(QUIZ_HISTORIES.RATING))
                .total(r.getValue(QUIZ_HISTORIES.TOTAL))
                .pdfFilename(r.getValue(QUIZ_HISTORIES.PDF_FILENAME))
                .csvFilename(r.getValue(QUIZ_HISTORIES.CSV_FILENAME))
                .session(QuizSession.builder().id(r.getValue(QUIZ_HISTORIES.SESSION_ID)).build())
                .user(User.builder().id(r.getValue(QUIZ_HISTORIES.USER_ID)).build())
                .build();
    }

    public static RecordMapper<Record, Quiz> getQuizFromRecordMapper() {
        return record -> Quiz
                .builder()
                .id(record.getValue(QUIZZES.ID))
                .title(record.getValue(QUIZZES.TITLE))
                .description(record.getValue(QUIZZES.DESCRIPTION))
                .total(record.getValue(QUIZZES.TOTAL))
                .author(User.builder().id(record.getValue(QUIZZES.AUTHOR_ID)).build())
                .active(record.getValue(QUIZZES.ACTIVE))
                .creationDate(record.getValue(QUIZZES.CREATION_DATE).toLocalDateTime())
                .group(getGroupFromQuizIfNotEmpty(record))
                .build();
    }

    private static Group getGroupFromQuizIfNotEmpty(Record record) {
        if (ObjectUtils.isNotEmpty(record.getValue(QUIZZES.GROUP_ID)))
            return Group.builder().id(record.getValue(QUIZZES.GROUP_ID)).build();
        else
            return Group.builder().build();

    }

    public static RecordMapper<Record, QuizSession> getRecordQuizSessionRecordMapper() {
        return r -> QuizSession
                .builder()
                .id(r.getValue(QUIZ_SESSIONS.ID))
                .date(r.getValue(QUIZ_SESSIONS.DATE).toLocalDateTime())
                .user(User.builder().id(r.getValue(QUIZ_SESSIONS.USER_ID)).build())
                .quiz(Quiz.builder().id(r.getValue(QUIZ_SESSIONS.QUIZ_ID)).build())
                .build();
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.kopivad.quizzes.domain.db;


import com.kopivad.quizzes.domain.db.tables.Answers;
import com.kopivad.quizzes.domain.db.tables.ApiClients;
import com.kopivad.quizzes.domain.db.tables.EvaluationSteps;
import com.kopivad.quizzes.domain.db.tables.Questions;
import com.kopivad.quizzes.domain.db.tables.QuizAnswers;
import com.kopivad.quizzes.domain.db.tables.QuizHistories;
import com.kopivad.quizzes.domain.db.tables.QuizSessions;
import com.kopivad.quizzes.domain.db.tables.Quizzes;
import com.kopivad.quizzes.domain.db.tables.Usr;
import com.kopivad.quizzes.domain.db.tables.UsrRoles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = -1770187052;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.answers</code>.
     */
    public final Answers ANSWERS = com.kopivad.quizzes.domain.db.tables.Answers.ANSWERS;

    /**
     * The table <code>public.api_clients</code>.
     */
    public final ApiClients API_CLIENTS = com.kopivad.quizzes.domain.db.tables.ApiClients.API_CLIENTS;

    /**
     * The table <code>public.evaluation_steps</code>.
     */
    public final EvaluationSteps EVALUATION_STEPS = com.kopivad.quizzes.domain.db.tables.EvaluationSteps.EVALUATION_STEPS;

    /**
     * The table <code>public.questions</code>.
     */
    public final Questions QUESTIONS = com.kopivad.quizzes.domain.db.tables.Questions.QUESTIONS;

    /**
     * The table <code>public.quiz_answers</code>.
     */
    public final QuizAnswers QUIZ_ANSWERS = com.kopivad.quizzes.domain.db.tables.QuizAnswers.QUIZ_ANSWERS;

    /**
     * The table <code>public.quiz_histories</code>.
     */
    public final QuizHistories QUIZ_HISTORIES = com.kopivad.quizzes.domain.db.tables.QuizHistories.QUIZ_HISTORIES;

    /**
     * The table <code>public.quiz_sessions</code>.
     */
    public final QuizSessions QUIZ_SESSIONS = com.kopivad.quizzes.domain.db.tables.QuizSessions.QUIZ_SESSIONS;

    /**
     * The table <code>public.quizzes</code>.
     */
    public final Quizzes QUIZZES = com.kopivad.quizzes.domain.db.tables.Quizzes.QUIZZES;

    /**
     * The table <code>public.usr</code>.
     */
    public final Usr USR = com.kopivad.quizzes.domain.db.tables.Usr.USR;

    /**
     * The table <code>public.usr_roles</code>.
     */
    public final UsrRoles USR_ROLES = com.kopivad.quizzes.domain.db.tables.UsrRoles.USR_ROLES;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Answers.ANSWERS,
            ApiClients.API_CLIENTS,
            EvaluationSteps.EVALUATION_STEPS,
            Questions.QUESTIONS,
            QuizAnswers.QUIZ_ANSWERS,
            QuizHistories.QUIZ_HISTORIES,
            QuizSessions.QUIZ_SESSIONS,
            Quizzes.QUIZZES,
            Usr.USR,
            UsrRoles.USR_ROLES);
    }
}

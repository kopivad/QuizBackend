/*
 * This file is generated by jOOQ.
 */
package com.kopivad.quizzes.domain.db;


import com.kopivad.quizzes.domain.db.tables.*;

import javax.annotation.processing.Generated;


/**
 * Convenience access to all tables in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.answers</code>.
     */
    public static final Answers ANSWERS = Answers.ANSWERS;

    /**
     * The table <code>public.api_clients</code>.
     */
    public static final ApiClients API_CLIENTS = ApiClients.API_CLIENTS;

    /**
     * The table <code>public.questions</code>.
     */
    public static final Questions QUESTIONS = Questions.QUESTIONS;

    /**
     * The table <code>public.quizzes</code>.
     */
    public static final Quizzes QUIZZES = Quizzes.QUIZZES;

    /**
     * The table <code>public.usr</code>.
     */
    public static final Usr USR = Usr.USR;
}
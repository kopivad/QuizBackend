/*
 * This file is generated by jOOQ.
 */
package com.kopivad.quizzes.domain.db;


import com.kopivad.quizzes.domain.db.tables.*;
import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;

import javax.annotation.processing.Generated;


/**
 * A class modelling indexes of tables of the <code>public</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index ANSWERS_PKEY = Indexes0.ANSWERS_PKEY;
    public static final Index API_CLIENTS_PKEY = Indexes0.API_CLIENTS_PKEY;
    public static final Index QUESTIONS_PKEY = Indexes0.QUESTIONS_PKEY;
    public static final Index QUIZZES_PKEY = Indexes0.QUIZZES_PKEY;
    public static final Index USR_PKEY = Indexes0.USR_PKEY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index ANSWERS_PKEY = Internal.createIndex("answers_pkey", Answers.ANSWERS, new OrderField[] { Answers.ANSWERS.ID }, true);
        public static Index API_CLIENTS_PKEY = Internal.createIndex("api_clients_pkey", ApiClients.API_CLIENTS, new OrderField[] { ApiClients.API_CLIENTS.ID }, true);
        public static Index QUESTIONS_PKEY = Internal.createIndex("questions_pkey", Questions.QUESTIONS, new OrderField[] { Questions.QUESTIONS.ID }, true);
        public static Index QUIZZES_PKEY = Internal.createIndex("quizzes_pkey", Quizzes.QUIZZES, new OrderField[] { Quizzes.QUIZZES.ID }, true);
        public static Index USR_PKEY = Internal.createIndex("usr_pkey", Usr.USR, new OrderField[] { Usr.USR.ID }, true);
    }
}
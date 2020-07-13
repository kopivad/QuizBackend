/*
 * This file is generated by jOOQ.
 */
package com.kopivad.quizzes.domain.db.tables;


import com.kopivad.quizzes.domain.db.Indexes;
import com.kopivad.quizzes.domain.db.Keys;
import com.kopivad.quizzes.domain.db.Public;
import com.kopivad.quizzes.domain.db.tables.records.QuizAnswersRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class QuizAnswers extends TableImpl<QuizAnswersRecord> {

    private static final long serialVersionUID = -1818105398;

    /**
     * The reference instance of <code>public.quiz_answers</code>
     */
    public static final QuizAnswers QUIZ_ANSWERS = new QuizAnswers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<QuizAnswersRecord> getRecordType() {
        return QuizAnswersRecord.class;
    }

    /**
     * The column <code>public.quiz_answers.id</code>.
     */
    public final TableField<QuizAnswersRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.quiz_answers.answer_id</code>.
     */
    public final TableField<QuizAnswersRecord, Long> ANSWER_ID = createField(DSL.name("answer_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.quiz_answers.question_id</code>.
     */
    public final TableField<QuizAnswersRecord, Long> QUESTION_ID = createField(DSL.name("question_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.quiz_answers.session_id</code>.
     */
    public final TableField<QuizAnswersRecord, Long> SESSION_ID = createField(DSL.name("session_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>public.quiz_answers</code> table reference
     */
    public QuizAnswers() {
        this(DSL.name("quiz_answers"), null);
    }

    /**
     * Create an aliased <code>public.quiz_answers</code> table reference
     */
    public QuizAnswers(String alias) {
        this(DSL.name(alias), QUIZ_ANSWERS);
    }

    /**
     * Create an aliased <code>public.quiz_answers</code> table reference
     */
    public QuizAnswers(Name alias) {
        this(alias, QUIZ_ANSWERS);
    }

    private QuizAnswers(Name alias, Table<QuizAnswersRecord> aliased) {
        this(alias, aliased, null);
    }

    private QuizAnswers(Name alias, Table<QuizAnswersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> QuizAnswers(Table<O> child, ForeignKey<O, QuizAnswersRecord> key) {
        super(child, key, QUIZ_ANSWERS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.QUIZ_RESULTS_PK);
    }

    @Override
    public UniqueKey<QuizAnswersRecord> getPrimaryKey() {
        return Keys.QUIZ_RESULTS_PK;
    }

    @Override
    public List<UniqueKey<QuizAnswersRecord>> getKeys() {
        return Arrays.<UniqueKey<QuizAnswersRecord>>asList(Keys.QUIZ_RESULTS_PK);
    }

    @Override
    public List<ForeignKey<QuizAnswersRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<QuizAnswersRecord, ?>>asList(Keys.QUIZ_ANSWERS__QUIZ_RESULTS_ANSWERS_ID_FK, Keys.QUIZ_ANSWERS__QUIZ_RESULTS_QUESTIONS_ID_FK, Keys.QUIZ_ANSWERS__QUIZ_RESULTS_QUIZ_SESSIONS_ID_FK);
    }

    public Answers answers() {
        return new Answers(this, Keys.QUIZ_ANSWERS__QUIZ_RESULTS_ANSWERS_ID_FK);
    }

    public Questions questions() {
        return new Questions(this, Keys.QUIZ_ANSWERS__QUIZ_RESULTS_QUESTIONS_ID_FK);
    }

    public QuizSessions quizSessions() {
        return new QuizSessions(this, Keys.QUIZ_ANSWERS__QUIZ_RESULTS_QUIZ_SESSIONS_ID_FK);
    }

    @Override
    public QuizAnswers as(String alias) {
        return new QuizAnswers(DSL.name(alias), this);
    }

    @Override
    public QuizAnswers as(Name alias) {
        return new QuizAnswers(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public QuizAnswers rename(String name) {
        return new QuizAnswers(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public QuizAnswers rename(Name name) {
        return new QuizAnswers(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, Long, Long, Long> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}

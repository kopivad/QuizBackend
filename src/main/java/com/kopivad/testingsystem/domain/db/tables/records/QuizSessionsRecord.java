/*
 * This file is generated by jOOQ.
 */
package com.kopivad.testingsystem.domain.db.tables.records;


import com.kopivad.testingsystem.domain.db.tables.QuizSessions;

import java.sql.Timestamp;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QuizSessionsRecord extends UpdatableRecordImpl<QuizSessionsRecord> implements Record4<Long, Timestamp, Long, Long> {

    private static final long serialVersionUID = -2146900741;

    /**
     * Setter for <code>public.quiz_sessions.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.quiz_sessions.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.quiz_sessions.created</code>.
     */
    public void setCreated(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.quiz_sessions.created</code>.
     */
    public Timestamp getCreated() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>public.quiz_sessions.quiz_id</code>.
     */
    public void setQuizId(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.quiz_sessions.quiz_id</code>.
     */
    public Long getQuizId() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>public.quiz_sessions.user_id</code>.
     */
    public void setUserId(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.quiz_sessions.user_id</code>.
     */
    public Long getUserId() {
        return (Long) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, Timestamp, Long, Long> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, Timestamp, Long, Long> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return QuizSessions.QUIZ_SESSIONS.ID;
    }

    @Override
    public Field<Timestamp> field2() {
        return QuizSessions.QUIZ_SESSIONS.CREATED;
    }

    @Override
    public Field<Long> field3() {
        return QuizSessions.QUIZ_SESSIONS.QUIZ_ID;
    }

    @Override
    public Field<Long> field4() {
        return QuizSessions.QUIZ_SESSIONS.USER_ID;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Timestamp component2() {
        return getCreated();
    }

    @Override
    public Long component3() {
        return getQuizId();
    }

    @Override
    public Long component4() {
        return getUserId();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Timestamp value2() {
        return getCreated();
    }

    @Override
    public Long value3() {
        return getQuizId();
    }

    @Override
    public Long value4() {
        return getUserId();
    }

    @Override
    public QuizSessionsRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public QuizSessionsRecord value2(Timestamp value) {
        setCreated(value);
        return this;
    }

    @Override
    public QuizSessionsRecord value3(Long value) {
        setQuizId(value);
        return this;
    }

    @Override
    public QuizSessionsRecord value4(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public QuizSessionsRecord values(Long value1, Timestamp value2, Long value3, Long value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached QuizSessionsRecord
     */
    public QuizSessionsRecord() {
        super(QuizSessions.QUIZ_SESSIONS);
    }

    /**
     * Create a detached, initialised QuizSessionsRecord
     */
    public QuizSessionsRecord(Long id, Timestamp created, Long quizId, Long userId) {
        super(QuizSessions.QUIZ_SESSIONS);

        set(0, id);
        set(1, created);
        set(2, quizId);
        set(3, userId);
    }
}

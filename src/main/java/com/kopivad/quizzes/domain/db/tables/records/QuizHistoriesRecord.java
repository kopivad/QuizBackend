/*
 * This file is generated by jOOQ.
 */
package com.kopivad.quizzes.domain.db.tables.records;


import com.kopivad.quizzes.domain.db.tables.QuizHistories;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


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
public class QuizHistoriesRecord extends UpdatableRecordImpl<QuizHistoriesRecord> implements Record7<Long, Long, Integer, Long, String, String, String> {

    private static final long serialVersionUID = -2134712673;

    /**
     * Setter for <code>public.quiz_histories.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.quiz_histories.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.quiz_histories.user_id</code>.
     */
    public void setUserId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.quiz_histories.user_id</code>.
     */
    public Long getUserId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.quiz_histories.total</code>.
     */
    public void setTotal(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.quiz_histories.total</code>.
     */
    public Integer getTotal() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.quiz_histories.session_id</code>.
     */
    public void setSessionId(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.quiz_histories.session_id</code>.
     */
    public Long getSessionId() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>public.quiz_histories.rating</code>.
     */
    public void setRating(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.quiz_histories.rating</code>.
     */
    public String getRating() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.quiz_histories.pdf_filename</code>.
     */
    public void setPdfFilename(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.quiz_histories.pdf_filename</code>.
     */
    public String getPdfFilename() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.quiz_histories.csv_filename</code>.
     */
    public void setCsvFilename(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.quiz_histories.csv_filename</code>.
     */
    public String getCsvFilename() {
        return (String) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, Long, Integer, Long, String, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Long, Long, Integer, Long, String, String, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return QuizHistories.QUIZ_HISTORIES.ID;
    }

    @Override
    public Field<Long> field2() {
        return QuizHistories.QUIZ_HISTORIES.USER_ID;
    }

    @Override
    public Field<Integer> field3() {
        return QuizHistories.QUIZ_HISTORIES.TOTAL;
    }

    @Override
    public Field<Long> field4() {
        return QuizHistories.QUIZ_HISTORIES.SESSION_ID;
    }

    @Override
    public Field<String> field5() {
        return QuizHistories.QUIZ_HISTORIES.RATING;
    }

    @Override
    public Field<String> field6() {
        return QuizHistories.QUIZ_HISTORIES.PDF_FILENAME;
    }

    @Override
    public Field<String> field7() {
        return QuizHistories.QUIZ_HISTORIES.CSV_FILENAME;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getUserId();
    }

    @Override
    public Integer component3() {
        return getTotal();
    }

    @Override
    public Long component4() {
        return getSessionId();
    }

    @Override
    public String component5() {
        return getRating();
    }

    @Override
    public String component6() {
        return getPdfFilename();
    }

    @Override
    public String component7() {
        return getCsvFilename();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getUserId();
    }

    @Override
    public Integer value3() {
        return getTotal();
    }

    @Override
    public Long value4() {
        return getSessionId();
    }

    @Override
    public String value5() {
        return getRating();
    }

    @Override
    public String value6() {
        return getPdfFilename();
    }

    @Override
    public String value7() {
        return getCsvFilename();
    }

    @Override
    public QuizHistoriesRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public QuizHistoriesRecord value2(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public QuizHistoriesRecord value3(Integer value) {
        setTotal(value);
        return this;
    }

    @Override
    public QuizHistoriesRecord value4(Long value) {
        setSessionId(value);
        return this;
    }

    @Override
    public QuizHistoriesRecord value5(String value) {
        setRating(value);
        return this;
    }

    @Override
    public QuizHistoriesRecord value6(String value) {
        setPdfFilename(value);
        return this;
    }

    @Override
    public QuizHistoriesRecord value7(String value) {
        setCsvFilename(value);
        return this;
    }

    @Override
    public QuizHistoriesRecord values(Long value1, Long value2, Integer value3, Long value4, String value5, String value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached QuizHistoriesRecord
     */
    public QuizHistoriesRecord() {
        super(QuizHistories.QUIZ_HISTORIES);
    }

    /**
     * Create a detached, initialised QuizHistoriesRecord
     */
    public QuizHistoriesRecord(Long id, Long userId, Integer total, Long sessionId, String rating, String pdfFilename, String csvFilename) {
        super(QuizHistories.QUIZ_HISTORIES);

        set(0, id);
        set(1, userId);
        set(2, total);
        set(3, sessionId);
        set(4, rating);
        set(5, pdfFilename);
        set(6, csvFilename);
    }
}

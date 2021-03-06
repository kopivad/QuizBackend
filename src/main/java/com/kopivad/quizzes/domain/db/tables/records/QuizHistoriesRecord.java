/*
 * This file is generated by jOOQ.
 */
package com.kopivad.quizzes.domain.db.tables.records;


import com.kopivad.quizzes.domain.db.tables.QuizHistories;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.processing.Generated;


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
public class QuizHistoriesRecord extends UpdatableRecordImpl<QuizHistoriesRecord> implements Record7<Long, Integer, String, Long, Long, String, String> {

    private static final long serialVersionUID = -1978205985;

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
     * Setter for <code>public.quiz_histories.total</code>.
     */
    public void setTotal(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.quiz_histories.total</code>.
     */
    public Integer getTotal() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.quiz_histories.rating</code>.
     */
    public void setRating(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.quiz_histories.rating</code>.
     */
    public String getRating() {
        return (String) get(2);
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
     * Setter for <code>public.quiz_histories.user_id</code>.
     */
    public void setUserId(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.quiz_histories.user_id</code>.
     */
    public Long getUserId() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>public.quiz_histories.pdf_file_name</code>.
     */
    public void setPdfFileName(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.quiz_histories.pdf_file_name</code>.
     */
    public String getPdfFileName() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.quiz_histories.csv_file_name</code>.
     */
    public void setCsvFileName(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.quiz_histories.csv_file_name</code>.
     */
    public String getCsvFileName() {
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
    public Row7<Long, Integer, String, Long, Long, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Long, Integer, String, Long, Long, String, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return QuizHistories.QUIZ_HISTORIES.ID;
    }

    @Override
    public Field<Integer> field2() {
        return QuizHistories.QUIZ_HISTORIES.TOTAL;
    }

    @Override
    public Field<String> field3() {
        return QuizHistories.QUIZ_HISTORIES.RATING;
    }

    @Override
    public Field<Long> field4() {
        return QuizHistories.QUIZ_HISTORIES.SESSION_ID;
    }

    @Override
    public Field<Long> field5() {
        return QuizHistories.QUIZ_HISTORIES.USER_ID;
    }

    @Override
    public Field<String> field6() {
        return QuizHistories.QUIZ_HISTORIES.PDF_FILE_NAME;
    }

    @Override
    public Field<String> field7() {
        return QuizHistories.QUIZ_HISTORIES.CSV_FILE_NAME;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getTotal();
    }

    @Override
    public String component3() {
        return getRating();
    }

    @Override
    public Long component4() {
        return getSessionId();
    }

    @Override
    public Long component5() {
        return getUserId();
    }

    @Override
    public String component6() {
        return getPdfFileName();
    }

    @Override
    public String component7() {
        return getCsvFileName();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getTotal();
    }

    @Override
    public String value3() {
        return getRating();
    }

    @Override
    public Long value4() {
        return getSessionId();
    }

    @Override
    public Long value5() {
        return getUserId();
    }

    @Override
    public String value6() {
        return getPdfFileName();
    }

    @Override
    public String value7() {
        return getCsvFileName();
    }

    @Override
    public QuizHistoriesRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public QuizHistoriesRecord value2(Integer value) {
        setTotal(value);
        return this;
    }

    @Override
    public QuizHistoriesRecord value3(String value) {
        setRating(value);
        return this;
    }

    @Override
    public QuizHistoriesRecord value4(Long value) {
        setSessionId(value);
        return this;
    }

    @Override
    public QuizHistoriesRecord value5(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public QuizHistoriesRecord value6(String value) {
        setPdfFileName(value);
        return this;
    }

    @Override
    public QuizHistoriesRecord value7(String value) {
        setCsvFileName(value);
        return this;
    }

    @Override
    public QuizHistoriesRecord values(Long value1, Integer value2, String value3, Long value4, Long value5, String value6, String value7) {
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
    public QuizHistoriesRecord(Long id, Integer total, String rating, Long sessionId, Long userId, String pdfFileName, String csvFileName) {
        super(QuizHistories.QUIZ_HISTORIES);

        set(0, id);
        set(1, total);
        set(2, rating);
        set(3, sessionId);
        set(4, userId);
        set(5, pdfFileName);
        set(6, csvFileName);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.kopivad.quizzes.domain.db.tables;


import com.kopivad.quizzes.domain.db.Indexes;
import com.kopivad.quizzes.domain.db.Keys;
import com.kopivad.quizzes.domain.db.Public;
import com.kopivad.quizzes.domain.db.tables.records.AnswersRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.processing.Generated;
import java.util.Arrays;
import java.util.List;


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
public class Answers extends TableImpl<AnswersRecord> {

    private static final long serialVersionUID = -1317304395;

    /**
     * The reference instance of <code>public.answers</code>
     */
    public static final Answers ANSWERS = new Answers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AnswersRecord> getRecordType() {
        return AnswersRecord.class;
    }

    /**
     * The column <code>public.answers.id</code>.
     */
    public final TableField<AnswersRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('answers_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.answers.is_right</code>.
     */
    public final TableField<AnswersRecord, Boolean> IS_RIGHT = createField(DSL.name("is_right"), org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.answers.text</code>.
     */
    public final TableField<AnswersRecord, String> TEXT = createField(DSL.name("text"), org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.answers.question_id</code>.
     */
    public final TableField<AnswersRecord, Long> QUESTION_ID = createField(DSL.name("question_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * Create a <code>public.answers</code> table reference
     */
    public Answers() {
        this(DSL.name("answers"), null);
    }

    /**
     * Create an aliased <code>public.answers</code> table reference
     */
    public Answers(String alias) {
        this(DSL.name(alias), ANSWERS);
    }

    /**
     * Create an aliased <code>public.answers</code> table reference
     */
    public Answers(Name alias) {
        this(alias, ANSWERS);
    }

    private Answers(Name alias, Table<AnswersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Answers(Name alias, Table<AnswersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Answers(Table<O> child, ForeignKey<O, AnswersRecord> key) {
        super(child, key, ANSWERS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ANSWERS_PKEY);
    }

    @Override
    public Identity<AnswersRecord, Long> getIdentity() {
        return Keys.IDENTITY_ANSWERS;
    }

    @Override
    public UniqueKey<AnswersRecord> getPrimaryKey() {
        return Keys.ANSWERS_PKEY;
    }

    @Override
    public List<UniqueKey<AnswersRecord>> getKeys() {
        return Arrays.<UniqueKey<AnswersRecord>>asList(Keys.ANSWERS_PKEY);
    }

    @Override
    public List<ForeignKey<AnswersRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AnswersRecord, ?>>asList(Keys.ANSWERS__ANSWERS_QUESTIONS_FK);
    }

    public Questions questions() {
        return new Questions(this, Keys.ANSWERS__ANSWERS_QUESTIONS_FK);
    }

    @Override
    public Answers as(String alias) {
        return new Answers(DSL.name(alias), this);
    }

    @Override
    public Answers as(Name alias) {
        return new Answers(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Answers rename(String name) {
        return new Answers(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Answers rename(Name name) {
        return new Answers(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, Boolean, String, Long> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}

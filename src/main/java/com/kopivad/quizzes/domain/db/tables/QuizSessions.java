/*
 * This file is generated by jOOQ.
 */
package com.kopivad.quizzes.domain.db.tables;


import com.kopivad.quizzes.domain.db.Indexes;
import com.kopivad.quizzes.domain.db.Keys;
import com.kopivad.quizzes.domain.db.Public;
import com.kopivad.quizzes.domain.db.tables.records.QuizSessionsRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.processing.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


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
public class QuizSessions extends TableImpl<QuizSessionsRecord> {

    private static final long serialVersionUID = 1682343524;

    /**
     * The reference instance of <code>public.quiz_sessions</code>
     */
    public static final QuizSessions QUIZ_SESSIONS = new QuizSessions();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<QuizSessionsRecord> getRecordType() {
        return QuizSessionsRecord.class;
    }

    /**
     * The column <code>public.quiz_sessions.id</code>.
     */
    public final TableField<QuizSessionsRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(DSL.field("nextval('quiz_sessions_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.quiz_sessions.quiz_id</code>.
     */
    public final TableField<QuizSessionsRecord, Long> QUIZ_ID = createField(DSL.name("quiz_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.quiz_sessions.user_id</code>.
     */
    public final TableField<QuizSessionsRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.quiz_sessions.creation_date</code>.
     */
    public final TableField<QuizSessionsRecord, Timestamp> CREATION_DATE = createField(DSL.name("creation_date"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * Create a <code>public.quiz_sessions</code> table reference
     */
    public QuizSessions() {
        this(DSL.name("quiz_sessions"), null);
    }

    /**
     * Create an aliased <code>public.quiz_sessions</code> table reference
     */
    public QuizSessions(String alias) {
        this(DSL.name(alias), QUIZ_SESSIONS);
    }

    /**
     * Create an aliased <code>public.quiz_sessions</code> table reference
     */
    public QuizSessions(Name alias) {
        this(alias, QUIZ_SESSIONS);
    }

    private QuizSessions(Name alias, Table<QuizSessionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private QuizSessions(Name alias, Table<QuizSessionsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> QuizSessions(Table<O> child, ForeignKey<O, QuizSessionsRecord> key) {
        super(child, key, QUIZ_SESSIONS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.QUIZ_SESSIONS_PK);
    }

    @Override
    public Identity<QuizSessionsRecord, Long> getIdentity() {
        return Keys.IDENTITY_QUIZ_SESSIONS;
    }

    @Override
    public UniqueKey<QuizSessionsRecord> getPrimaryKey() {
        return Keys.QUIZ_SESSIONS_PK;
    }

    @Override
    public List<UniqueKey<QuizSessionsRecord>> getKeys() {
        return Arrays.<UniqueKey<QuizSessionsRecord>>asList(Keys.QUIZ_SESSIONS_PK);
    }

    @Override
    public List<ForeignKey<QuizSessionsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<QuizSessionsRecord, ?>>asList(Keys.QUIZ_SESSIONS__QUIZ_SESSIONS_QUIZZES_ID_FK, Keys.QUIZ_SESSIONS__QUIZ_SESSIONS_USERS_ID_FK);
    }

    public Quizzes quizzes() {
        return new Quizzes(this, Keys.QUIZ_SESSIONS__QUIZ_SESSIONS_QUIZZES_ID_FK);
    }

    public Users users() {
        return new Users(this, Keys.QUIZ_SESSIONS__QUIZ_SESSIONS_USERS_ID_FK);
    }

    @Override
    public QuizSessions as(String alias) {
        return new QuizSessions(DSL.name(alias), this);
    }

    @Override
    public QuizSessions as(Name alias) {
        return new QuizSessions(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public QuizSessions rename(String name) {
        return new QuizSessions(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public QuizSessions rename(Name name) {
        return new QuizSessions(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, Long, Long, Timestamp> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}

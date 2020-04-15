/*
 * This file is generated by jOOQ.
 */
package com.kopivad.quizzes.domain.db;


import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;

import javax.annotation.processing.Generated;


/**
 * Convenience access to all sequences in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.answers_id_seq</code>
     */
    public static final Sequence<Long> ANSWERS_ID_SEQ = new SequenceImpl<Long>("answers_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.api_clients_id_seq</code>
     */
    public static final Sequence<Long> API_CLIENTS_ID_SEQ = new SequenceImpl<Long>("api_clients_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.questions_id_seq</code>
     */
    public static final Sequence<Long> QUESTIONS_ID_SEQ = new SequenceImpl<Long>("questions_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.quizzes_id_seq</code>
     */
    public static final Sequence<Long> QUIZZES_ID_SEQ = new SequenceImpl<Long>("quizzes_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.usr_id_seq</code>
     */
    public static final Sequence<Long> USR_ID_SEQ = new SequenceImpl<Long>("usr_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));
}

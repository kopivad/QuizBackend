/*
 * This file is generated by jOOQ.
 */
package com.kopivad.quizzes.domain.db.tables.records;


import com.kopivad.quizzes.domain.db.tables.Usr;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.processing.Generated;
import java.sql.Timestamp;


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
public class UsrRecord extends UpdatableRecordImpl<UsrRecord> implements Record6<Long, Timestamp, String, String, String, String> {

    private static final long serialVersionUID = -1794475546;

    /**
     * Setter for <code>public.usr.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.usr.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.usr.creation_date</code>.
     */
    public void setCreationDate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.usr.creation_date</code>.
     */
    public Timestamp getCreationDate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>public.usr.email</code>.
     */
    public void setEmail(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.usr.email</code>.
     */
    public String getEmail() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.usr.name</code>.
     */
    public void setName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.usr.name</code>.
     */
    public String getName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.usr.password</code>.
     */
    public void setPassword(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.usr.password</code>.
     */
    public String getPassword() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.usr.role</code>.
     */
    public void setRole(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.usr.role</code>.
     */
    public String getRole() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, Timestamp, String, String, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Long, Timestamp, String, String, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Usr.USR.ID;
    }

    @Override
    public Field<Timestamp> field2() {
        return Usr.USR.CREATION_DATE;
    }

    @Override
    public Field<String> field3() {
        return Usr.USR.EMAIL;
    }

    @Override
    public Field<String> field4() {
        return Usr.USR.NAME;
    }

    @Override
    public Field<String> field5() {
        return Usr.USR.PASSWORD;
    }

    @Override
    public Field<String> field6() {
        return Usr.USR.ROLE;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Timestamp component2() {
        return getCreationDate();
    }

    @Override
    public String component3() {
        return getEmail();
    }

    @Override
    public String component4() {
        return getName();
    }

    @Override
    public String component5() {
        return getPassword();
    }

    @Override
    public String component6() {
        return getRole();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Timestamp value2() {
        return getCreationDate();
    }

    @Override
    public String value3() {
        return getEmail();
    }

    @Override
    public String value4() {
        return getName();
    }

    @Override
    public String value5() {
        return getPassword();
    }

    @Override
    public String value6() {
        return getRole();
    }

    @Override
    public UsrRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public UsrRecord value2(Timestamp value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public UsrRecord value3(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public UsrRecord value4(String value) {
        setName(value);
        return this;
    }

    @Override
    public UsrRecord value5(String value) {
        setPassword(value);
        return this;
    }

    @Override
    public UsrRecord value6(String value) {
        setRole(value);
        return this;
    }

    @Override
    public UsrRecord values(Long value1, Timestamp value2, String value3, String value4, String value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UsrRecord
     */
    public UsrRecord() {
        super(Usr.USR);
    }

    /**
     * Create a detached, initialised UsrRecord
     */
    public UsrRecord(Long id, Timestamp creationDate, String email, String name, String password, String role) {
        super(Usr.USR);

        set(0, id);
        set(1, creationDate);
        set(2, email);
        set(3, name);
        set(4, password);
        set(5, role);
    }
}
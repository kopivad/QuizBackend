/*
 * This file is generated by jOOQ.
 */
package com.kopivad.quizzes.domain.db.tables;


import com.kopivad.quizzes.domain.db.Keys;
import com.kopivad.quizzes.domain.db.Public;
import com.kopivad.quizzes.domain.db.tables.records.GroupsUsersRecord;
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
        "jOOQ version:3.12.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GroupsUsers extends TableImpl<GroupsUsersRecord> {

    private static final long serialVersionUID = -1096483732;

    /**
     * The reference instance of <code>public.groups_users</code>
     */
    public static final GroupsUsers GROUPS_USERS = new GroupsUsers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GroupsUsersRecord> getRecordType() {
        return GroupsUsersRecord.class;
    }

    /**
     * The column <code>public.groups_users.group_id</code>.
     */
    public final TableField<GroupsUsersRecord, Long> GROUP_ID = createField(DSL.name("group_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.groups_users.user_id</code>.
     */
    public final TableField<GroupsUsersRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>public.groups_users</code> table reference
     */
    public GroupsUsers() {
        this(DSL.name("groups_users"), null);
    }

    /**
     * Create an aliased <code>public.groups_users</code> table reference
     */
    public GroupsUsers(String alias) {
        this(DSL.name(alias), GROUPS_USERS);
    }

    /**
     * Create an aliased <code>public.groups_users</code> table reference
     */
    public GroupsUsers(Name alias) {
        this(alias, GROUPS_USERS);
    }

    private GroupsUsers(Name alias, Table<GroupsUsersRecord> aliased) {
        this(alias, aliased, null);
    }

    private GroupsUsers(Name alias, Table<GroupsUsersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> GroupsUsers(Table<O> child, ForeignKey<O, GroupsUsersRecord> key) {
        super(child, key, GROUPS_USERS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<ForeignKey<GroupsUsersRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<GroupsUsersRecord, ?>>asList(Keys.GROUPS_USERS__GROUPS_USERS_GROUPS_ID_FK, Keys.GROUPS_USERS__GROUPS_USERS_USERS_ID_FK);
    }

    public Groups groups() {
        return new Groups(this, Keys.GROUPS_USERS__GROUPS_USERS_GROUPS_ID_FK);
    }

    public Users users() {
        return new Users(this, Keys.GROUPS_USERS__GROUPS_USERS_USERS_ID_FK);
    }

    @Override
    public GroupsUsers as(String alias) {
        return new GroupsUsers(DSL.name(alias), this);
    }

    @Override
    public GroupsUsers as(Name alias) {
        return new GroupsUsers(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GroupsUsers rename(String name) {
        return new GroupsUsers(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GroupsUsers rename(Name name) {
        return new GroupsUsers(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}

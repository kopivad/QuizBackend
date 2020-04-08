package com.kopivad.quizzes.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtils {

    public static void commitTransaction(Connection connection) throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public static void rollbackTransaction(Connection connection) throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }

    public static void startTransaction(Connection connection, boolean readOnly) throws SQLException {
        connection.setAutoCommit(false);
        connection.setReadOnly(readOnly);
    }
}

package com.kopivad.quizzes.repository.jdbc.utils;

import com.kopivad.quizzes.exeption.DaoOperationException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {
    private static final String URL = "jdbc:postgresql://localhost:5432/quizzes_test";
    private static final String USERNAME = "vad";
    private static final String PASSWORD = "1234";
    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";

    private static final String CREATE_USERS_TABLE_IF_NOT_EXISTS = "create table if not exists users\n" +
            "(\n" +
            "id serial not null\n" +
            "constraint users_pkey\n" +
            "primary key,\n" +
            "name text not null,\n" +
            "email text not null,\n" +
            "password text not null,\n" +
            "creation_date timestamp default now() not null,\n" +
            "role text default USER\n" +
            ");\n" +
            "\n";

    public static DataSource createTestDefaultPgDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    public static void createUsersTableIfNotExists(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery(CREATE_USERS_TABLE_IF_NOT_EXISTS);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }
}

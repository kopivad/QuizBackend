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
            "\tid serial not null\n" +
            "\t\tconstraint users_pkey\n" +
            "\t\t\tprimary key,\n" +
            "\tname text not null,\n" +
            "\temail text not null,\n" +
            "\tpassword text not null,\n" +
            "\tcreation_date timestamp default now() not null,\n" +
            "\trole text default USER\n" +
            ");\n" +
            "\n" +
            "alter table users owner to vad;\n" +
            "\n";

    private static final String CREATE_QUIZZES_TABLE_IF_NOT_EXISTS = "create table if not exists quizzes\n" +
            "(\n" +
            "\tid bigserial not null\n" +
            "\t\tconstraint table_name_pk\n" +
            "\t\t\tprimary key,\n" +
            "\ttitle text not null,\n" +
            "\tdescription text not null,\n" +
            "\tactive boolean default true not null,\n" +
            "\tcreation_date timestamp default now() not null,\n" +
            "\tauthor_id bigserial not null\n" +
            "\t\tconstraint quizzes_users_fk\n" +
            "\t\t\treferences users\n" +
            "\t\t\t\ton delete cascade\n" +
            ");\n" +
            "\n" +
            "alter table quizzes owner to vad;\n" +
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
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeQuery(CREATE_USERS_TABLE_IF_NOT_EXISTS);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    public static void createQuizzesTableIfNotExists(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeQuery(CREATE_QUIZZES_TABLE_IF_NOT_EXISTS);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }
}

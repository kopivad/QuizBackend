package com.kopivad.quizzes.utils;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class TestUtils {
    private static final String URL = "jdbc:postgresql://localhost:5432/quizzes_test";
    private static final String USERNAME = "vad";
    private static final String PASSWORD = "1234";
    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";

    public static DataSource createTestDefaultPgDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    public static DSLContext createTestDefaultDSLContext() {
        return DSL.using(TestUtils.createTestDefaultPgDataSource(), SQLDialect.POSTGRES);
    }
}

package com.kopivad.quizzes.repository.jdbc;

import com.kopivad.quizzes.domain.api.ApiClient;
import com.kopivad.quizzes.exeption.DaoOperationException;
import com.kopivad.quizzes.repository.ApiClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@AllArgsConstructor
public class ApiClientRepositoryImpl implements ApiClientRepository {
    private final DataSource dataSource;

    private static final String SELECT_BY_USERNAME = "SELECT * FROM api_clients WHERE username = ?;";

    @Override
    public ApiClient findByUsername(String name) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement selectStatement = connection.prepareStatement(SELECT_BY_USERNAME);
            selectStatement.setString(1, name);
            ResultSet rs = selectStatement.executeQuery();
            rs.next();
            return parseRow(rs);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    private ApiClient parseRow(ResultSet rs) throws SQLException {
        return ApiClient.builder().username(rs.getString(1)).password(rs.getString(2)).build();
    }
}

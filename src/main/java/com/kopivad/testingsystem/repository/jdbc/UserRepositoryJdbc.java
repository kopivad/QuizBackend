package com.kopivad.testingsystem.repository.jdbc;

import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.exeption.DaoOperationException;
import com.kopivad.testingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryJdbc implements UserRepository {
    private final String SELECT_ALL_SQL = "SELECT * FROM Users;";
    private final String SELECT_USER_BY_ID_SQL = "SELECT * FROM Users WHERE id = ?;";
    private final String SELECT_USER_BY_EMAIL_SQL = "SELECT * FROM Users WHERE email = ?;";
    private final String INSERT_USER_SQL = "INSERT INTO Users(name, email, password, creationDate, role) VALUES (?, ?, ?, now(), ?);";
    private final String UPDATE_USER_SQL = "UPDATE Users SET name = ?, email = ?, password = ?, role = ? WHERE id = ?;";
    private final String DELETE_USER_SQL = "DELETE FROM Users WHERE id = ?;";

    private final DataSource dataSource;

    @Override
    public List<User> findAll() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL_SQL);
            return collectToList(rs);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    @Override
    public User findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            return findUserById(id, connection);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    @Override
    public User save(User user) {
        try (Connection connection = dataSource.getConnection()) {
            return saveUser(user, connection);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    private User saveUser(User user, Connection connection) throws SQLException {
        PreparedStatement insertUserStatement = prepareInsertStatement(user, connection);
        executeUpdate(insertUserStatement, "User was not created");
        Long id = fetchGeneratedId(insertUserStatement);
        return this.findById(id);
    }

    @Override
    public User update(Long id, User user) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement updateStatement = prepareUpdateStatement(id, user, connection);
            executeUpdate(updateStatement, "Account was not updated");
            return user;
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Cannot update User with id = %d", user.getId()), e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement deleteStatement = prepareDeleteStatement(id, connection);
            executeUpdate(deleteStatement, "Account was not deleted");
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Cannot delete User with id = %d", id), e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Connection connection = dataSource.getConnection()) {
            return findUserByEmail(email, connection);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    private User findUserByEmail(String email, Connection connection) throws SQLException {
        PreparedStatement selectByEmailStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_SQL);
        selectByEmailStatement.setString(1, email);
        ResultSet resultSet = selectByEmailStatement.executeQuery();
        resultSet.next();
        return parseRow(resultSet);
    }

    private User findUserById(Long id, Connection connection) throws SQLException {
        PreparedStatement selectByIdStatement = connection.prepareStatement(SELECT_USER_BY_ID_SQL);
        selectByIdStatement.setLong(1, id);
        ResultSet rs = selectByIdStatement.executeQuery();
        rs.next();
        return parseRow(rs);
    }

    private PreparedStatement prepareDeleteStatement(Long id, Connection connection) throws SQLException {
        PreparedStatement deleteUserStatement = connection.prepareStatement(DELETE_USER_SQL);
        deleteUserStatement.setLong(1, id);
        return deleteUserStatement;
    }

    private PreparedStatement prepareUpdateStatement(Long id, User user, Connection connection) {
        try {
            PreparedStatement updateStatement = connection.prepareStatement(UPDATE_USER_SQL);
            fillStatementWithUserData(updateStatement, user);
            updateStatement.setLong(5, id);
            return updateStatement;
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Cannot prepare update statement for account id = %d", user.getId()), e);
        }
    }

    private PreparedStatement prepareInsertStatement(User user, Connection connection) throws SQLException {
        PreparedStatement insertStatement = connection.prepareStatement(INSERT_USER_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
        return fillStatementWithUserData(insertStatement, user);
    }

    private PreparedStatement fillStatementWithUserData(PreparedStatement insertUserStatement, User user) throws SQLException {
        insertUserStatement.setString(1, user.getName());
        insertUserStatement.setString(2, user.getEmail());
        insertUserStatement.setString(3, user.getPassword());
        insertUserStatement.setString(4, user.getRole());
        return insertUserStatement;
    }

    private Long fetchGeneratedId(PreparedStatement insertStatement) throws SQLException {
        ResultSet generatedKeys = insertStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getLong(1);
        } else {
            throw new DaoOperationException("Can not obtain an account ID");
        }
    }

    private void executeUpdate(PreparedStatement insertStatement, String errorMessage) throws SQLException {
        int rowsAffected = insertStatement.executeUpdate();
        if (rowsAffected == 0) {
            throw new DaoOperationException(errorMessage);
        }
    }



    private List<User> collectToList(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = parseRow(rs);
            users.add(user);
        }
        return users;
    }

    private User parseRow(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getLong(1))
                .name(rs.getString(2))
                .email(rs.getString(3))
                .password(rs.getString(4))
                .creationDate(rs.getTimestamp(5))
                .role(rs.getString(6))
                .build();
    }

}

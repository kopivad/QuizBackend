package com.kopivad.quizzes.repository.jdbc;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.exeption.DaoOperationException;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.repository.jdbc.utils.JdbcUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.kopivad.quizzes.repository.jdbc.utils.JdbcUtils.*;

@Repository
@RequiredArgsConstructor
public class UserRepositoryJdbc implements UserRepository {
    private static final String SELECT_ALL_SQL = "SELECT * FROM Users;";
    private static final String SELECT_USER_BY_ID_SQL = "SELECT * FROM Users WHERE id = ?;";
    private static final String SELECT_USER_BY_EMAIL_SQL = "SELECT * FROM Users WHERE email = ?;";
    private static final String INSERT_USER_SQL = "INSERT INTO Users(name, email, password, creation_date, role) VALUES (?, ?, ?, now(), ?);";
    private static final String UPDATE_USER_SQL = "UPDATE Users SET name = ?, email = ?, password = ?, role = ? WHERE id = ?;";
    private static final String DELETE_USER_SQL = "DELETE FROM Users WHERE id = ?;";

    private final DataSource dataSource;

    @Override
    public List<User> findAll() {
        return findAllUsers();
    }

    private List<User> findAllUsers() {
        try (Connection connection = dataSource.getConnection()) {
            ResultSet rs;
            startTransaction(connection, true);
            try {
                Statement statement = connection.createStatement();
                rs = statement.executeQuery(SELECT_ALL_SQL);
            } catch (SQLException e) {
                rollbackTransaction(connection);
                throw new DaoOperationException("Can not find all users");
            }
            commitTransaction(connection);
            return collectToList(rs);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    @Override
    public User findById(Long id) {
        return findUserById(id);
    }

    private User findUserById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            ResultSet rs;
            startTransaction(connection, true);
            try {
                PreparedStatement selectByIdStatement = connection.prepareStatement(SELECT_USER_BY_ID_SQL);
                selectByIdStatement.setLong(1, id);
                rs = selectByIdStatement.executeQuery();
                rs.next();
            } catch (SQLException e) {
                rollbackTransaction(connection);
                throw new DaoOperationException("Can not find user by id!");
            }
            commitTransaction(connection);
            return parseRow(rs);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    @Override
    public User save(User user) {
        return saveUser(user);
    }

    private User saveUser(User user) {
        try (Connection connection = dataSource.getConnection()) {
            Long id;
            startTransaction(connection, false);
            try {
                PreparedStatement insertUserStatement = prepareInsertStatement(user, connection);
                executeUpdate(insertUserStatement, "User was not created");
                id = fetchGeneratedId(insertUserStatement);
            } catch (SQLException e) {
                rollbackTransaction(connection);
                throw new DaoOperationException("Can not save user!");
            }
            commitTransaction(connection);
            return this.findById(id);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    @Override
    public User update(Long id, User user) {
        return updateUser(id, user);
    }

    private User updateUser(Long id, User user) {
        try (Connection connection = dataSource.getConnection()) {
            startTransaction(connection, false);
            try {
                PreparedStatement updateStatement = prepareUpdateStatement(id, user, connection);
                executeUpdate(updateStatement, "User was not updated");
            } catch (SQLException e) {
                rollbackTransaction(connection);
                throw new DaoOperationException("Can not update user!");
            }
            commitTransaction(connection);
            return this.findById(id);
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Cannot update User with id = %d", user.getId()), e);
        }
    }

    @Override
    public void delete(Long id) {
        deleteUser(id);
    }

    private void deleteUser(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            startTransaction(connection, false);
            try {
                PreparedStatement deleteStatement = prepareDeleteStatement(id, connection);
                executeUpdate(deleteStatement, "User was not deleted");
            } catch (SQLException e) {
                rollbackTransaction(connection);
                throw new DaoOperationException("Can not delete user");
            }
            commitTransaction(connection);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    @Override
    public User findByEmail(String email) {
        return findUserByEmail(email);
    }

    private User findUserByEmail(String email) {
        try (Connection connection = dataSource.getConnection()) {
            ResultSet rs;
            startTransaction(connection, true);
            try {
                PreparedStatement selectByEmailStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_SQL);
                selectByEmailStatement.setString(1, email);
                rs = selectByEmailStatement.executeQuery();
                rs.next();
            } catch (SQLException e) {
                rollbackTransaction(connection);
                throw new DaoOperationException("Can not find user by email!");
            }
            commitTransaction(connection);
            return parseRow(rs);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
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
            throw new DaoOperationException(String.format("Cannot prepare update statement for user id = %d", id), e);
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
            throw new DaoOperationException("Can not obtain an user ID");
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
            .creationDate(rs.getTimestamp(5).toLocalDateTime())
            .role(rs.getString(6))
            .build();
    }

}

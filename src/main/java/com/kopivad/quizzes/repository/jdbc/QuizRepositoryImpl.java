package com.kopivad.quizzes.repository.jdbc;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.exeption.DaoOperationException;
import com.kopivad.quizzes.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Repository
public class QuizRepositoryImpl implements QuizRepository {
    private final DataSource dataSource;

    private static final String SELECT_ALL_SQL = "SELECT * FROM Quizzes;";
    private static final String SELECT_QUIZ_BY_ID_SQL = "SELECT * FROM Quizzes WHERE id = ?;";
    private static final String INSERT_QUIZ_SQL = "INSERT INTO Quizzes(title, description, active, author_id) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_QUIZ_SQL = "UPDATE Quizzes SET title = ?, description = ?, active = ?, author_id = ? WHERE id = ?;";
    private static final String DELETE_QUIZ_SQL = "DELETE FROM Quizzes WHERE id = ?;";

    @Override
    public List<Quiz> findAll() {
        try (Connection connection = dataSource.getConnection()) {
            Statement selectAllStatement = connection.createStatement();
            ResultSet rs = selectAllStatement.executeQuery(SELECT_ALL_SQL);
            List<Quiz> quizzes = new ArrayList<>();
            while (rs.next()) {
                quizzes.add(parseRow(rs));
            }
            return quizzes;
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    @Override
    public Quiz findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            return findQuizById(id, connection);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    private Quiz findQuizById(Long id, Connection connection) throws SQLException {
        PreparedStatement selectByIdStatement = connection.prepareStatement(SELECT_QUIZ_BY_ID_SQL);
        selectByIdStatement.setLong(1, id);
        ResultSet rs = selectByIdStatement.executeQuery();
        rs.next();
        return parseRow(rs);
    }

    @Override
    public Quiz save(Quiz quiz) {
        try (Connection connection = dataSource.getConnection()) {
            return saveQuiz(quiz, connection);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    private Quiz saveQuiz(Quiz quiz, Connection connection) throws SQLException {
        PreparedStatement insertStatement =  prepareInsertStatement(quiz, connection);
        executeUpdate(insertStatement, "Quiz was not created");
        Long id = fetchGeneratedId(insertStatement);
        return this.findById(id);
    }

    private Long fetchGeneratedId(PreparedStatement insertStatement) throws SQLException {
        ResultSet generatedKeys = insertStatement.getGeneratedKeys();
        if (generatedKeys.next()){
            return generatedKeys.getLong(1);
        } else {
            throw new DaoOperationException("Can not obtain a quiz id");
        }
    }

    private PreparedStatement prepareInsertStatement(Quiz quiz, Connection connection) throws SQLException {
        PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUIZ_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
        return fillStatementWithQuizData(insertStatement, quiz);
    }

    private PreparedStatement fillStatementWithQuizData(PreparedStatement insertStatement, Quiz quiz) throws SQLException {
            insertStatement.setString(1, quiz.getTitle());
            insertStatement.setString(2, quiz.getDescription());
            insertStatement.setBoolean(3, quiz.isActive());
            insertStatement.setLong(4, quiz.getAuthor().getId());
            return insertStatement;
    }

    private void executeUpdate(PreparedStatement insertStatement, String errorMessage) throws SQLException {
        int rowsAffected = insertStatement.executeUpdate();
        if (rowsAffected == 0) {
            throw new DaoOperationException(errorMessage);
        }
    }

    @Override
    public Quiz update(Long id, Quiz quiz) {
        try (Connection connection = dataSource.getConnection()) {
            return updateQuiz(id, quiz, connection);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    private Quiz updateQuiz(Long id, Quiz quiz, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = prepareUpdateStatement(id, quiz, connection);
        executeUpdate(preparedStatement, "Quiz was not updated");
        return this.findById(id);
    }

    private PreparedStatement prepareUpdateStatement(Long id, Quiz quiz, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUIZ_SQL);
            fillStatementWithQuizData(preparedStatement, quiz);
            preparedStatement.setLong(5, id);
            return preparedStatement;
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Cannot prepare update statement for quiz id = %d", id), e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            deleteQuiz(id, connection);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    private void deleteQuiz(Long id, Connection connection) throws SQLException {
        PreparedStatement deleteStatement = prepareDeleteStatement(id, connection);
        executeUpdate(deleteStatement, "Quiz was not deleted");
    }

    private PreparedStatement prepareDeleteStatement(Long id, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUIZ_SQL);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private Quiz parseRow(ResultSet rs) throws SQLException {
        return Quiz.builder()
                .id(rs.getLong(1))
                .title(rs.getString(2))
                .description(rs.getString(3))
                .active(rs.getBoolean(4))
                .creationDate(rs.getTimestamp(5))
                .author(User.builder().id(rs.getLong(6)).build())
                .build();
    }
}

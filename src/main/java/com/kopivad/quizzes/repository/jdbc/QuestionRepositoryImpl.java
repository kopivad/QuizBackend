package com.kopivad.quizzes.repository.jdbc;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.exeption.DaoOperationException;
import com.kopivad.quizzes.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.kopivad.quizzes.utils.JdbcUtils.*;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {
    private final DataSource dataSource;

    private static final String SELECT_ALL_SQL = "SELECT * FROM Questions;";
    private static final String SELECT_QUESTION_BY_ID_SQL = "SELECT * FROM Questions WHERE id = ?;";
    private static final String INSERT_QUESTION_SQL = "INSERT INTO Questions(title, quiz_id) VALUES (?, ?);";
    private static final String UPDATE_QUESTION_SQL = "UPDATE Questions SET title = ?, quiz_id = ? WHERE id = ?;";
    private static final String DELETE_QUESTION_SQL = "DELETE FROM Questions WHERE id = ?;";


    @Override
    public List<Question> findAll() {
        return findAllQuestions();
    }

    private List<Question> findAllQuestions() {
        try (Connection connection = dataSource.getConnection()) {
            ResultSet resultSet;
            startTransaction(connection, true);
            try {
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(SELECT_ALL_SQL);
            } catch (SQLException e) {
                rollbackTransaction(connection);
                throw new DaoOperationException("Can not find all question!");
            }
            commitTransaction(connection);
            return collectToList(resultSet);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    private List<Question> collectToList(ResultSet resultSet) throws SQLException {
        List<Question> questions = new ArrayList<>();
        while (resultSet.next()) {
            questions.add(parseRow(resultSet));
        }
        return questions;
    }

    private Question parseRow(ResultSet resultSet) throws SQLException {
        return Question.builder()
                .id(resultSet.getLong(1))
                .title(resultSet.getString(2))
                .quiz(Quiz.builder().id(resultSet.getLong(3)).build())
                .build();
    }

    @Override
    public Question findById(Long id) {
        return findQuestionById(id);
    }

    private Question findQuestionById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            ResultSet rs;
            startTransaction(connection, true);
            try {
                PreparedStatement selectStatement = prepareSelectStatement(id, connection);
                rs = selectStatement.executeQuery();
                rs.next();
            } catch (SQLException e) {
                rollbackTransaction(connection);
                throw new DaoOperationException("Can not find question by id!");
            }
            commitTransaction(connection);
            return parseRow(rs);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    private PreparedStatement prepareSelectStatement(Long id, Connection connection) {
        try {
            PreparedStatement selectStatement = connection.prepareStatement(SELECT_QUESTION_BY_ID_SQL);
            selectStatement.setLong(1, id);
            return selectStatement;
        } catch (SQLException e) {
            throw new DaoOperationException("Can not prepare select by id statement");
        }
    }

    @Override
    public Question save(Question question) {
            return saveQuestion(question);
    }

    private Question saveQuestion(Question question) {
        try (Connection connection = dataSource.getConnection()) {
            Long id;
            startTransaction(connection, false);
            try {
                PreparedStatement insertStatement = prepareInsertStatement(connection, question);
                executeUpdate(insertStatement, "Question was not created!");
                id = fetchGeneratedId(insertStatement);
            } catch (SQLException e) {
                rollbackTransaction(connection);
                throw new DaoOperationException("Can not save question!");
            }
            commitTransaction(connection);
            return findById(id);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }

    }

    private void executeUpdate(PreparedStatement insertStatement, String error) throws SQLException {
        int rowAffected = insertStatement.executeUpdate();
        if (rowAffected == 0) {
            throw new DaoOperationException(error);
        }
    }

    private PreparedStatement prepareInsertStatement(Connection connection, Question question) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUESTION_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            return fillStatementWithData(question, insertStatement);
        } catch (SQLException e) {
            throw new DaoOperationException("Can not prepare statement!");
        }
    }

    private Long fetchGeneratedId(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            return resultSet.getLong(1);
        } else throw new DaoOperationException("Can`t get id");

    }

    private PreparedStatement fillStatementWithData(Question question, PreparedStatement prepareStatement) throws SQLException {
        prepareStatement.setString(1, question.getTitle());
        prepareStatement.setLong(2, question.getQuiz().getId());
        return prepareStatement;
    }

    @Override
    public Question update(Long id, Question question) {
        return updateQuestion(id, question);
    }

    private Question updateQuestion(Long id, Question question) {
        try (Connection connection = dataSource.getConnection()) {
            startTransaction(connection, false);
            try {
                PreparedStatement updateStatement = prepareUpdateStatement(id, question, connection);
                executeUpdate(updateStatement, "Question was not created!");
            } catch (SQLException e) {
                rollbackTransaction(connection);
                throw new DaoOperationException("Can not update question");
            }
            commitTransaction(connection);
            return findById(id);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    private PreparedStatement prepareUpdateStatement(Long id, Question question, Connection connection) {
        try {
            PreparedStatement updateStatement = connection.prepareStatement(UPDATE_QUESTION_SQL);
            fillStatementWithData(question, updateStatement);
            updateStatement.setLong(3, id);
            return updateStatement;
        } catch (SQLException e) {
            throw new DaoOperationException("Can not prepare update statement");
        }
    }

    @Override
    public void delete(Long id) {
        deleteQuestion(id);
    }

    private void deleteQuestion(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            startTransaction(connection, false);
            try {
                PreparedStatement deleteStatement = prepareDeleteStatement(id, connection);
                executeUpdate(deleteStatement, "Question was not deleted!");
            } catch (SQLException e) {
                rollbackTransaction(connection);
                throw new DaoOperationException("Can not delete question");
            }
            commitTransaction(connection);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }


    private PreparedStatement prepareDeleteStatement(Long id, Connection connection) {
        try {
            PreparedStatement deleteStatement = connection.prepareStatement(DELETE_QUESTION_SQL);
            deleteStatement.setLong(1, id);
            return deleteStatement;
        } catch (SQLException e) {
            throw new DaoOperationException("Can not prepare delete statement!");
        }
    }
}

package ru.javawebinar.basejava.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import ru.javawebinar.basejava.exception.StorageException;

public class SQLHelper {

    private final ConnectionFactory connectionFactory;

    public SQLHelper(String dbURL, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbURL, dbUser, dbPassword);
    }

    public void execute(String query) {
        execute(query, PreparedStatement::execute);
    }

    public <T> T execute(String query, SQLProcessor<T> processor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            return processor.process(statement);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T execute(String query, Connection connection, SQLProcessor<T> processor) {
        try (var statement = connection.prepareStatement(query)) {
            return processor.process(statement);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SQLTransaction<T> processor) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T res = processor.process(connection);
                connection.commit();
                return res;
            } catch (SQLException e) {
                connection.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}

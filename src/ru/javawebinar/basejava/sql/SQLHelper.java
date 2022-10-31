package ru.javawebinar.basejava.sql;

import static ru.javawebinar.basejava.storage.Storage.LOG;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

public class SQLHelper {

    private final ConnectionFactory connectionFactory;

    public SQLHelper(String dbURL, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbURL, dbUser, dbPassword);
    }

    public <T> T execute(String query, SQLProcessor<T> processor) {
        try ( Connection connection = connectionFactory.getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
            return processor.process(statement);
        } catch (SQLException e) {
            if (isViolatesConstraint(e)) {
                LOG.log(Level.WARNING, "Resume {0} is already exists", e.getMessage());
                throw new ExistStorageException(e.getMessage());
            } else {
                throw new StorageException(e);
            }
        }
    }

    private boolean isViolatesConstraint(SQLException e) {
        return e.getSQLState().startsWith("23");
    }
}

package ru.javawebinar.basejava.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ru.javawebinar.basejava.exception.StorageException;

public class SQLHelper {

    private final ConnectionFactory connectionFactory;

    public SQLHelper(String dbURL, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbURL, dbUser, dbPassword);
    }

    public ResultSet execute(String query, SQLProcessor processor) {
        try {
            Connection connection = connectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            return processor.process(statement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}

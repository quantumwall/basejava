package ru.javawebinar.basejava.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SQLTransaction<T> {

    T process(Connection connection) throws SQLException;
}

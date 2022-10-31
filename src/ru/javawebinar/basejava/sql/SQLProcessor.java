package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLProcessor<T> {

    T process(PreparedStatement statement) throws SQLException;
}

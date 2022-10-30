package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLProcessor {

    ResultSet process(PreparedStatement statement) throws SQLException;
}

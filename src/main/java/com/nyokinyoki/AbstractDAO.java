package com.nyokinyoki;

import java.sql.*;
import java.util.*;

import com.nyokinyoki.Course.CourseDAO;
import com.nyokinyoki.Timetable.TimeslotDAO;
import com.nyokinyoki.Timetable.TimestampDAO;
import com.nyokinyoki.Timetable.TimetableDAO;

public sealed abstract class AbstractDAO<T> permits TimetableDAO, CourseDAO, TimeslotDAO, TimestampDAO {
    protected final String url = "jdbc:sqlite:NyokinyokiPirorin.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load SQLite JDBC driver", e);
        }
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public abstract List<T> getAll();

    public abstract void add(T item);

    public abstract void remove(int id);

    public abstract void removeAll();

    protected void executeUpdate(String sql) {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute update", e);
        }
    }
}

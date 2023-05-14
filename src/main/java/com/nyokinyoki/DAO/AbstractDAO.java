package com.nyokinyoki.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

package src.main.java.com.nyokinyoki.nyokinyoki_pirorin;

import java.sql.*;
import java.util.*;

public abstract class AbstractDAO<T> {

    protected final String url = "jdbc:sqlite:src/main/resources/NyokinyokiPirorin.db";

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

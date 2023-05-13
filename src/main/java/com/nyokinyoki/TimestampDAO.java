package com.nyokinyoki;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimestampDAO extends AbstractDAO<LocalDateTime> {

    public TimestampDAO() {
        String sql = "CREATE TABLE IF NOT EXISTS timestamps (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timestamp INTEGER NOT NULL)";
        executeUpdate(sql);
    }

    @Override
    public List<LocalDateTime> getAll() {
        String sql = "SELECT * FROM timestamps";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            List<LocalDateTime> timestamps = new ArrayList<>();
            while (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                LocalDateTime localDateTime = timestamp.toLocalDateTime();
                timestamps.add(localDateTime);
            }
            return timestamps;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get timestamps", e);
        }
    }

    @Override
    public void add(LocalDateTime localDateTime) {
        String sql = "INSERT INTO timestamps (timestamp) VALUES (?)";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.valueOf(localDateTime));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add timestamp", e);
        }
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM timestamps WHERE id = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove timestamp", e);
        }
    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM timestamps";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove all timestamps", e);
        }
    }

    public List<LocalDateTime> getBetween(LocalDateTime from, LocalDateTime to) {
        String sql = "SELECT * FROM timestamps WHERE timestamp >= ? AND timestamp <= ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.valueOf(from));
            statement.setTimestamp(2, Timestamp.valueOf(to));

            try (ResultSet resultSet = statement.executeQuery()) {
                List<LocalDateTime> timestamps = new ArrayList<>();

                while (resultSet.next()) {
                    Timestamp timestamp = resultSet.getTimestamp("timestamp");
                    LocalDateTime localDateTime = timestamp.toLocalDateTime();
                    timestamps.add(localDateTime);
                }

                return timestamps;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get timestamps from" + from + "to" + to, e);
        }
    }

    public List<LocalDateTime> getByDate(LocalDateTime date) {
        LocalDateTime from = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime to = date.withHour(23).withMinute(59).withSecond(59);
        return getBetween(from, to);
    }
}

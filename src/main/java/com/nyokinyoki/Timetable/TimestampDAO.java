package com.nyokinyoki.Timetable;

import java.util.*;

import com.nyokinyoki.AbstractDAO;
import com.nyokinyoki.Course.Course;

import java.sql.*;
import java.time.*;
import java.time.format.*;

public final class TimestampDAO extends AbstractDAO<LocalDateTime> {
    private static TimestampDAO instance = null;

    private TimestampDAO() {
        String sql = "CREATE TABLE IF NOT EXISTS timestamps (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timestamp TEXT NOT NULL)";
        executeUpdate(sql);
    }

    public static synchronized TimestampDAO getInstance() {
        if (instance == null) {
            instance = new TimestampDAO();
        }
        return instance;
    }

    @Override
    public List<LocalDateTime> getAll() {
        String sql = "SELECT * FROM timestamps ORDER BY timestamp ASC";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            List<LocalDateTime> timestamps = new ArrayList<>();
            while (resultSet.next()) {
                String timestamp = resultSet.getString("timestamp");
                LocalDateTime localDateTime = LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
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
            statement.setString(1, localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
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

    public List<LocalDateTime> getByDate(LocalDate date) {
        String sql = "SELECT * FROM timestamps WHERE strftime('%w', date(timestamp) = ? ORDER BY timestamp ASC";

        int dayOfWeek = date.getDayOfWeek().getValue() % 7;

        List<LocalDateTime> timestamps = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, date.format(DateTimeFormatter.ISO_LOCAL_DATE));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String timestamp = resultSet.getString("timestamp");
                    LocalDateTime localDateTime = LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    timestamps.add(localDateTime);
                }
                return timestamps;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get timestamps for day of week: " + dayOfWeek, e);
        }
    }

    public List<LocalDateTime> getByCourse(Course course) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM timestamps WHERE ");

        for (int i = 0; i < course.getTimeslots().size(); i++) {
            if (i > 0) {
                sqlBuilder.append(" OR ");
            }
            sqlBuilder.append("( time(timestamp) BETWEEN time(?) AND time(?))");
        }
        sqlBuilder.append(" ORDER BY timestamp ASC");

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString())) {
            int index = 1;
            for (Timeslot timeslot : course.getTimeslots()) {
                statement.setString(index++, timeslot.getStartStampTimeStart().toLocalTime()
                        .format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                statement.setString(index++,
                        timeslot.getEndStampTimeEnd().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                List<LocalDateTime> timestamps = new ArrayList<>();
                while (resultSet.next()) {
                    String timestamp = resultSet.getString("timestamp");
                    LocalDateTime localDateTime = LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    for (Timeslot timeslot : course.getTimeslots()) {
                        if (timeslot.getDayOfWeek() == localDateTime.getDayOfWeek().getValue()) {
                            timestamps.add(localDateTime);
                        }
                    }
                }
                return timestamps;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get timestamps for course " + course.getId(), e);
        }
    }

    public List<LocalDateTime> getByDateAndTimeslot(LocalDate date, Timeslot timeslot) {
        String sql = "SELECT * FROM timestamps WHERE date(timestamp) = ? AND time(timestamp) BETWEEN time(?) AND time(?) ORDER BY timestamp ASC";

        List<LocalDateTime> timestamps = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, date.toString());
            statement.setString(2, timeslot.getStartStampTimeStart().toLocalTime().toString());
            statement.setString(3, timeslot.getEndStampTimeEnd().toLocalTime().toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String timestamp = resultSet.getString("timestamp");
                    LocalDateTime localDateTime = LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    timestamps.add(localDateTime);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get timestamps for date and time slot: " + date + " " + timeslot, e);
        }
        return timestamps;
    }
}

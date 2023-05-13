package com.nyokinyoki;

import java.util.*;
import java.sql.*;
import java.time.*;
import java.time.format.*;

public class TimestampDAO extends AbstractDAO<LocalDateTime> {

    public TimestampDAO() {
        String sql = "CREATE TABLE IF NOT EXISTS timestamps (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timestamp TEXT NOT NULL)";
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
        String sql = "SELECT * FROM timestamps WHERE strftime('%w', datetime(timestamp)) = ?";

        int dayOfWeek = date.getDayOfWeek().getValue() % 7;

        List<LocalDateTime> timestamps = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, dayOfWeek);

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

    public List<LocalDateTime> getByDayOfWeek(DayOfWeek dayOfWeek) {
        String sql = "SELECT * FROM timestamps WHERE strftime('%w', datetime(timestamp)) = ?";
        List<LocalDateTime> timestamps = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            int sqliteDayOfWeek = dayOfWeek.getValue() % 7;
            statement.setInt(1, sqliteDayOfWeek);

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

    public List<LocalDateTime> getByTimeSlot(TimeSlot timeSlot) {
        String sql = "SELECT * FROM timestamps WHERE strftime('%w', timestamp) = ? AND time(timestamp) BETWEEN time(?) AND time(?)";

        int dayOfWeek = timeSlot.getDayOfWeek();
        LocalDateTime startStampTimeStart = timeSlot.getStartStampTimeStart();
        LocalDateTime endStampTimeEnd = timeSlot.getEndStampTimeEnd();

        List<LocalDateTime> timestamps = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, dayOfWeek);
            statement.setString(2, startStampTimeStart.toLocalTime().toString());
            statement.setString(3, endStampTimeEnd.toLocalTime().toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String timestamp = resultSet.getString("timestamp");
                    LocalDateTime localDateTime = LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    timestamps.add(localDateTime);
                }
                return timestamps;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get timestamps for time slot: " + timeSlot, e);
        }
    }

    public List<LocalDateTime> getByCourse(Course course) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM timestamps WHERE ");

        for (int i = 0; i < course.getTimeSlots().size(); i++) {
            if (i > 0) {
                sqlBuilder.append(" OR ");
            }
            sqlBuilder.append("(strftime('%w', timestamp) = ? AND strftime('%H:%M', timestamp) BETWEEN ? AND ?)");
        }

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString())) {
            int index = 1;
            for (TimeSlot timeSlot : course.getTimeSlots()) {
                statement.setInt(index++, timeSlot.getDayOfWeek());
                statement.setString(index++, timeSlot.getStartStampTimeStart().toLocalTime().toString());
                statement.setString(index++, timeSlot.getEndStampTimeEnd().toLocalTime().toString());
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                List<LocalDateTime> timestamps = new ArrayList<>();
                while (resultSet.next()) {
                    String timestamp = resultSet.getString("timestamp");
                    LocalDateTime localDateTime = LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    timestamps.add(localDateTime);
                }
                return timestamps;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get timestamps for course " + course.getId(), e);
        }
    }

    public List<LocalDateTime> getByDateAndTimeSlot(LocalDate date, TimeSlot timeSlot) {
        String sql = "SELECT * FROM timestamps WHERE date(timestamp) = ? AND time(timestamp) BETWEEN time(?) AND time(?)";

        List<LocalDateTime> timestamps = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, date.toString());
            statement.setString(2, timeSlot.getStartStampTimeStart().toLocalTime().toString());
            statement.setString(3, timeSlot.getEndStampTimeEnd().toLocalTime().toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String timestamp = resultSet.getString("timestamp");
                    LocalDateTime localDateTime = LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    timestamps.add(localDateTime);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get timestamps for date and time slot: " + date + " " + timeSlot, e);
        }
        return timestamps;
    }

}

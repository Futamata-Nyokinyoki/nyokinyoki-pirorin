package com.nyokinyoki;

import java.sql.*;
import java.util.*;

public class TimeslotDAO extends AbstractDAO<Timeslot> {
    private static TimeslotDAO instance = null;

    private TimeslotDAO() {
        String sql = "CREATE TABLE IF NOT EXISTS timeslots (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "courseId INTEGER NOT NULL," + "dayOfWeek INTEGER NOT NULL," + "beginPeriod INTEGER NOT NULL,"
                + "endPeriod INTEGER NOT NULL," + "FOREIGN KEY(id) REFERENCES courses(courseId)" + ");";
        executeUpdate(sql);
    }

    public static synchronized TimeslotDAO getInstance() {
        if (instance == null) {
            instance = new TimeslotDAO();
        }
        return instance;
    }

    @Override
    public List<Timeslot> getAll() {
        String sql = "SELECT * FROM timeslots";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Timeslot> timeslots = new ArrayList<>();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int courseId = resultSet.getInt("courseId");
                    int dayOfWeek = resultSet.getInt("dayOfWeek");
                    int beginPeriod = resultSet.getInt("beginPeriod");
                    int endPeriod = resultSet.getInt("endPeriod");

                    Timeslot timeslot = new Timeslot(id, courseId, dayOfWeek, beginPeriod, endPeriod);
                    timeslots.add(timeslot);
                }

                return timeslots;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all time slots", e);
        }
    }

    @Override
    public void add(Timeslot timeslot) {
        String sql = "INSERT INTO timeslots (courseId, dayOfWeek, beginPeriod, endPeriod) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, timeslot.getCourseId());
            statement.setInt(2, timeslot.getDayOfWeek());
            statement.setInt(3, timeslot.getBeginPeriod());
            statement.setInt(4, timeslot.getEndPeriod());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add time slot", e);
        }
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM timeslots WHERE id = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove time slot", e);
        }
    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM timeslots";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove all time slots", e);
        }
    }

    public List<Timeslot> getByCourseId(int courseId) {
        String sql = "SELECT * FROM timeslots WHERE courseId = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseId);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Timeslot> timeslots = new ArrayList<>();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int dayOfWeek = resultSet.getInt("dayOfWeek");
                    int beginPeriod = resultSet.getInt("beginPeriod");
                    int endPeriod = resultSet.getInt("endPeriod");

                    Timeslot timeslot = new Timeslot(id, courseId, dayOfWeek, beginPeriod, endPeriod);
                    timeslots.add(timeslot);
                }

                return timeslots;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get time slots by course id", e);
        }
    }

    public List<Timeslot> getByPeriod(int dayOfWeek, int beginPeriod) {
        String sql = "SELECT * FROM timeslots WHERE dayOfWeek = ? AND beginPeriod = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, dayOfWeek);
            statement.setInt(2, beginPeriod);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Timeslot> timeslots = new ArrayList<>();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int courseId = resultSet.getInt("courseId");
                    int endPeriod = resultSet.getInt("endPeriod");

                    Timeslot timeslot = new Timeslot(id, courseId, dayOfWeek, beginPeriod, endPeriod);
                    timeslots.add(timeslot);
                }

                return timeslots;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get time slots by dayOfWeek and beginPeriod", e);
        }
    }

}

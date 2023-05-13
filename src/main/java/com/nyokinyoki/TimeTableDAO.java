package com.nyokinyoki;

import java.sql.*;
import java.util.*;

public class TimeTableDAO extends AbstractDAO<Course> {

    public TimeTableDAO() {
        String sql = "CREATE TABLE IF NOT EXISTS time_tables (" + "courseId INTEGER PRIMARY KEY,"
                + "FOREIGN KEY(courseId) REFERENCES courses(id)" + ");";
        executeUpdate(sql);
    }

    @Override
    public List<Course> getAll() {
        String sql = "SELECT * FROM time_tables;";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Course> courses = new ArrayList<>();

            while (resultSet.next()) {
                int courseId = resultSet.getInt("courseId");
                Course course = new CourseDAO().getById(courseId);
                courses.add(course);
            }

            return courses;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get courses from timetable", e);
        }
    }

    @Override
    public void add(Course course) {
        String sql = "INSERT INTO time_tables (courseId) VALUES (?);";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, course.getId());
            statement.executeUpdate();
            return;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add course to timetable", e);
        }
    }

    @Override
    public void remove(int courseId) {
        String sql = "DELETE FROM time_tables WHERE courseId = ?;";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseId);
            statement.executeUpdate();
            return;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove course from timetable", e);
        }
    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM time_tables;";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
            return;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove all courses from timetable", e);
        }
    }

}

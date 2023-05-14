package com.nyokinyoki.Timetable;

import java.sql.*;
import java.util.*;

import com.nyokinyoki.AbstractDAO;
import com.nyokinyoki.Course.Course;
import com.nyokinyoki.Course.CourseDAO;

public final class TimetableDAO extends AbstractDAO<Course> {
    private static TimetableDAO instance = null;

    private TimetableDAO() {
        String sql = "CREATE TABLE IF NOT EXISTS timetable (" + "courseId INTEGER PRIMARY KEY,"
                + "FOREIGN KEY(courseId) REFERENCES courses(id)" + ");";
        executeUpdate(sql);
    }

    public static synchronized TimetableDAO getInstance() {
        if (instance == null) {
            instance = new TimetableDAO();
        }
        return instance;
    }

    @Override
    public List<Course> getAll() {
        String sql = "SELECT * FROM timetable;";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Course> courses = new ArrayList<>();

            while (resultSet.next()) {
                int courseId = resultSet.getInt("courseId");
                Course course = CourseDAO.getInstance().getById(courseId);
                courses.add(course);
            }

            return courses;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get courses from timetable", e);
        }
    }

    @Override
    public void add(Course course) {
        String sql = "INSERT INTO timetable (courseId) VALUES (?);";

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
        String sql = "DELETE FROM timetable WHERE courseId = ?;";

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
        String sql = "DELETE FROM timetable;";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
            return;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove all courses from timetable", e);
        }
    }

}
